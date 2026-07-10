package pt.viabilize.blissandroidchallenge.repository

import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import pt.viabilize.blissandroidchallenge.data.GithubDatasource
import pt.viabilize.blissandroidchallenge.data.local.dao.EmojiDao
import pt.viabilize.blissandroidchallenge.data.local.model.EmojiEntity
import retrofit2.Response

class GitHubRepositoryTest {

    @MockK
    lateinit var githubDatasource: GithubDatasource

    @MockK
    lateinit var emojiDao: EmojiDao

    @MockK
    lateinit var response: Response<Map<String, String>>

    @InjectMockKs
    lateinit var gitHubRepositoryImpl: GitHubRepositoryImpl

    val responseBody = mapOf( "1" to "First", "2" to "Second", "3" to "third")
    val emojiEntityList = listOf(
        EmojiEntity("1", "First"),
        EmojiEntity("2", "Second"),
        EmojiEntity("3", "third")
    )

    @Before
    fun setup () {
        MockKAnnotations.init(this)

        coEvery { githubDatasource.listEmojis() } returns response
        every { response.body() } returns responseBody
        every { emojiDao.insertAll(*anyVararg<EmojiEntity>()) } returns Unit
    }

    @Test
    fun testGetEmojis_noLocalData() {
        every { emojiDao.getAll() } returns emptyList()

        val result = runBlocking {
            gitHubRepositoryImpl.getEmojiList()
        }

        assertNotNull(result)
        assertEquals(3, result.size)
        verify(exactly = 1) { emojiDao.insertAll(*anyVararg<EmojiEntity>()) }
    }

    @Test
    fun testGetEmojis_withLocalData() {
        every { emojiDao.getAll() } returns emojiEntityList

        val result = runBlocking {
            gitHubRepositoryImpl.getEmojiList()
        }

        assertNotNull(result)
        assertEquals(3, result.size)

        verify(exactly = 0) { emojiDao.insertAll(*anyVararg<EmojiEntity>()) }
    }
}