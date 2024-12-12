package ifsp.project.welnessmind.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ifsp.project.welnessmind.domain.model.AccountService
import ifsp.project.welnessmind.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(): AccountService {
    override val currentUser: Flow<User?>  // Fluxo de dados assíncronos para representar o usuário atual (podendo ser nulo)
        get() = callbackFlow { // emite valores baseado em eventos, como alterações no estado de autenticação do usuário no Firebase Auth
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { User(it.uid) }) // envia o usuário atual para o fluxo, ou null se não estiver autenticado
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) } // remove o listener quando o fluxo é fechado
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty() // retorna o ID do usuário como uma string

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await() // operador !! garante que não seja nulo
    }
}