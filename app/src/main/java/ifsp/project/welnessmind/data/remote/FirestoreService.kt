package ifsp.project.welnessmind.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun saveUserName(uid: String, name: String) {
        firestore.collection("users").document(uid).set(
            mapOf(
                "name" to name
            )
        )
    }

    suspend fun getUserName(uid: String): String? {
        return firestore.collection("users").document(uid).get().await().getString("name")
    }

    suspend fun validatePassword(uid: String, password: String): Boolean {
        val userData = firestore.collection("users").document(uid).get().await()
        val storedHash = userData.getString("password")
        return storedHash == password.hashCode().toString()
    }

    fun saveUserData(uid: String, name: String, email: String, password: String) {
        firestore.collection("users").document(uid).set(
            mapOf(
                "name" to name,
                "email" to email,
                "password" to password.hashCode().toString()
            )
        )
    }

    suspend fun getUserData(uid: String): Map<String, String>? {
        val document = firestore.collection("users").document(uid).get().await()
        return if (document.exists()) {
            mapOf(
                "name" to (document.getString("name") ?: ""),
                "email" to (document.getString("email") ?: "")
            )
        } else null
    }

    suspend fun updateUserPassword(uid: String, newPassword: String) {
        firestore.collection("users").document(uid)
            .update("password", newPassword.hashCode().toString())
            .await()
    }
}