package com.example.talkcompanion.feature.phrase.functions

import android.util.Log
import com.example.talkcompanion.data.model.phrase.PhraseEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import java.util.concurrent.CompletableFuture

class PhraseFirebaseFunctionsKtTest {

    private lateinit var mockAuth: FirebaseAuth
    private lateinit var mockDatabase: FirebaseDatabase
    private lateinit var mockDataReference: DatabaseReference
    private lateinit var userPhrases: List<PhraseEntity>

    suspend fun wait() {
        kotlinx.coroutines.delay(1000)
    }

    @Before
    fun setUp() = runBlocking {
        mockAuth = FirebaseAuth.getInstance()
        mockDatabase = FirebaseDatabase.getInstance()
        mockDataReference = mockDatabase.getReference("Phrases")
        userPhrases = listOf()
        cleanData()
        //Se espera a que termine el borrado
        wait()
    }

    @Test
    fun getFirebasePhraseListByUserName() {
        val future = CompletableFuture<List<PhraseEntity>?>()

        //Se realiza login a firebase con usuario de prueba
        mockAuth.signInWithEmailAndPassword("testing-junit@testing.com","Test.1234")
            .addOnCompleteListener { task ->
                if (task.isComplete){

                    //Se guardan de manera secuencial 3 frasdes
                    addFirebasePhraseList("Prueba1",listOf()){ result1 ->
                        Log.d("testid",mockAuth.currentUser?.uid+"")
                        addFirebasePhraseList("Prueba2",result1){ result2 ->
                            Log.d("testid",mockAuth.currentUser?.uid+"")
                            addFirebasePhraseList("Prueba3",result2){ result3 ->
                                Log.d("testid",mockAuth.currentUser?.uid+"")

                                //Se hace un get para obetener todas las frases
                                getFirebasePhraseListByUserName(){ result4 ->
                                    future.complete(result4)
                                }
                            }
                        }
                    }
                }
            }

        assertNotNull("Obetner listado de frases de usuario",future.get())
        assertEquals(3, future.get()?.size ?: 0)
    }

    @Test
    fun getPhraseMaxId() {
        val futureCurrentMax = CompletableFuture<Int>()
        val futureResultMax = CompletableFuture<Int>()

        //Se realiza login a firebase con usuario de prueba
        mockAuth.signInWithEmailAndPassword("testing-junit@testing.com","Test.1234")
            .addOnCompleteListener { task ->
                if (task.isComplete){

                    //Se obtiene el id maximo actual
                    getPhraseMaxId(){ result ->
                        futureCurrentMax.complete(result)
                        Log.d("max","Actual "+result)

                        //Se guardan 3 frases de forma secuencial
                        addFirebasePhraseList("Prueba1",listOf()){ result1 ->
                            Log.d("testid",mockAuth.currentUser?.uid+"")
                            addFirebasePhraseList("Prueba2",result1){ result2 ->
                                Log.d("testid",mockAuth.currentUser?.uid+"")
                                addFirebasePhraseList("Prueba3",result2){ result3 ->
                                    Log.d("testid",mockAuth.currentUser?.uid+"")

                                    //Tras el guardado de obtiene nuevamente el maximo
                                    getPhraseMaxId(){ result4 ->
                                        Log.d("max","Resultado "+result4)
                                        futureResultMax.complete(result4)
                                    }
                                }
                            }
                        }
                    }
                }
            }

        assertEquals(futureCurrentMax.get() + 3, futureResultMax.get())
    }

    @Test
    fun addFirebasePhraseList() {
        var fraseAgregada : PhraseEntity? = null
        val future = CompletableFuture<PhraseEntity?>()

        //Se realiza login a firebase con usuario de prueba
        mockAuth.signInWithEmailAndPassword("testing-junit@testing.com","Test.1234")
            .addOnCompleteListener { task ->
                if (task.isComplete){

                    //Se guarda nueva frase
                    addFirebasePhraseList("Prueba1",listOf()){ result ->

                        //Se busca la nueva frase en el resultado
                        fraseAgregada = result.find { it.phrase == "Prueba1" }
                        future.complete(fraseAgregada)
                    }
                }
            }


        assertNotNull("Prueba Agergar Frase",future.get())
    }

    @Test
    fun updateFirebaseUserPhrases() {
        var fraseAgregada : PhraseEntity? = null
        val future = CompletableFuture<PhraseEntity?>()

        //Se realiza login a firebase con usuario de prueba
        mockAuth.signInWithEmailAndPassword("testing-junit@testing.com","Test.1234")
            .addOnCompleteListener { task ->
                if (task.isComplete){

                    //Se guarda nueva frase
                    addFirebasePhraseList("Prueba1",listOf()){ result ->
                        if(result.isNotEmpty()){
                            //Se modifica la nueva frase
                            result[0].orderNumber = 10

                            //Se actualizan las frases
                            updateFirebaseUserPhrases(result){ isSuccess ->

                                //Se hace un get las frases
                                getFirebasePhraseListByUserName(){ result2 ->

                                    //Se busca la frase editada
                                    fraseAgregada = result2.find { it.phrase == "Prueba1" }
                                    future.complete(fraseAgregada)
                                }
                            }
                        }else{
                            future.complete(fraseAgregada)
                        }
                    }
                }
            }

        assertEquals(10, future.get()?.orderNumber ?: 0)
    }

    @Test
    fun deleteFirebasePhraseById() {
        val future = CompletableFuture<PhraseEntity?>()

        //Se realiza login a firebase con usuario de prueba
        mockAuth.signInWithEmailAndPassword("testing-junit@testing.com","Test.1234")
            .addOnCompleteListener { task ->
                if (task.isComplete){

                    //Se guarda nueva frase
                    addFirebasePhraseList("Prueba1",listOf()){ result ->

                        //Se busca la nueva frase en el resultado
                        val fraseAgregada = result.find { it.phrase == "Prueba1" }

                        //Se hace el borrado de la frase guardadd
                        Log.d("delete","id eliminado "+(fraseAgregada?.id ?: 0))
                        deleteFirebasePhraseById(fraseAgregada?.id ?: 0,result){ result2 ->

                            //Se hace una llamado a las frases del usuario
                            getFirebasePhraseListByUserName(){ result3 ->

                                //Se busca nuevamente la frase
                                val fraseBorrada = result3.find { it.phrase == "Prueba1" }
                                future.complete(fraseBorrada)
                            }
                        }
                    }
                }
            }


        assertNull("Prueba Eliminar Frase",future.get())

    }

    private fun cleanData() {

        //Borrar todas las frases creadas por el usuario de prueba
        mockAuth.signInWithEmailAndPassword("testing-junit@testing.com","Test.1234")
            .addOnCompleteListener { task ->
                if (task.isComplete){
                    val userId = mockAuth.currentUser?.uid

                    FirebaseDatabase.getInstance().getReference("Phrases")
                        .orderByChild("userId")
                        .equalTo(userId)
                        .get()
                        .addOnCompleteListener  { task ->
                            val phraseList = mutableListOf<PhraseEntity>()
                            if (task.isSuccessful) {
                                val snapshot = task.result
                                if (snapshot != null && snapshot.exists()) {
                                    for (childSnapshot in snapshot.children) {
                                        childSnapshot.ref.removeValue();
                                    }
                                }
                            }
                        }
                }
            }

    }
}