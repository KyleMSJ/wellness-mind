package ifsp.project.welnessmind.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ifsp.project.welnessmind.data.remote.FirestoreService
import ifsp.project.welnessmind.domain.model.User
import ifsp.project.welnessmind.domain.repository.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val firestoreService: FirestoreService
) : AccountService {
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
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            when {
                e.message?.contains("ERROR_USER_NOT_FOUND") == true -> throw Exception("ERROR_USER_NOT_FOUND")
                e.message?.contains("ERROR_WRONG_PASSWORD") == true -> throw Exception("ERROR_WRONG_PASSWORD")
                e.message?.contains("ERROR_INVALID_EMAIL") == true -> throw Exception("ERROR_INVALID_EMAIL")
                else -> throw e
            }
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            when {
                e.message?.contains("ERROR_EMAIL_ALREADY_IN_USE") == true -> throw Exception("ERROR_EMAIL_ALREADY_IN_USE")
                e.message?.contains("ERROR_INVALID_EMAIL") == true -> throw Exception("ERROR_INVALID_EMAIL")
                e.message?.contains("ERROR_WEAK_PASSWORD") == true -> throw Exception("ERROR_WEAK_PASSWORD")
                else -> throw e
            }
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await() // operador !! garante que não seja nulo
    }

    override suspend fun sendRecoveryEmail(email: String) {
        try {
            Firebase.auth.sendPasswordResetEmail(email).await()
        } catch (e: Exception) {
            when {
                e.message?.contains("ERROR_USER_NOT_FOUND") == true -> throw Exception("Usuário não encontrado")
                e.message?.contains("ERROR_INVALID_EMAIL") == true -> throw Exception("E-mail inválido")
                else -> throw Exception("Erro ao enviar e-mail de recuperação")
            }
        }
    }

    override suspend fun updatePassword(newPassword: String) {
        try {
            val user = Firebase.auth.currentUser
            if (user != null) {
                user.updatePassword(newPassword).await()
                // Aqui você deve atualizar a senha no Firestore também
                // Assumindo que você tem acesso ao FirestoreService
                firestoreService.updateUserPassword(user.uid, newPassword)
            } else {
                throw Exception("Usuário não está autenticado")
            }
        } catch (e: Exception) {
            when {
                else -> throw Exception("Erro ao atualizar a senha")
            }
        }
    }
}