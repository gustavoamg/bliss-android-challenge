package pt.blissapps.blissandroidchallenge.di

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HiltConfigurationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Before
    fun init() {
        // Executa a injeção de dependências no objeto de teste atual
        hiltRule.inject()
    }

    @Test
    fun verifyHiltInjection_ContextShouldNotBeNull() {
        // Se o Hilt estiver mal configurado, o teste falhará antes ou aqui
        assertNotNull(context)

        // Verifica se o contexto recuperado corresponde ao pacote correto do seu app
        assertEquals("pt.viabilize.blissandroidchallenge", context.packageName)
    }
}