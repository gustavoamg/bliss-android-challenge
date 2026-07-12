package pt.blissapps.blissandroidchallenge.repository

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import pt.blissapps.blissandroidchallenge.data.GithubDatasource
import pt.blissapps.blissandroidchallenge.data.exception.AvatarNotFoundException
import pt.blissapps.blissandroidchallenge.data.exception.NetworkException
import pt.blissapps.blissandroidchallenge.data.local.dao.AvatarDao
import pt.blissapps.blissandroidchallenge.data.local.dao.EmojiDao
import pt.blissapps.blissandroidchallenge.data.local.mapper.toAvatar
import pt.blissapps.blissandroidchallenge.data.local.mapper.toEntity
import pt.blissapps.blissandroidchallenge.data.local.model.AvatarEntity
import pt.blissapps.blissandroidchallenge.data.local.model.EmojiEntity
import pt.blissapps.blissandroidchallenge.data.remote.AvatarResponse
import retrofit2.Response

class GitHubRepositoryTest {

    @MockK
    lateinit var githubDatasource: GithubDatasource

    @MockK
    lateinit var emojiDao: EmojiDao

    @MockK
    lateinit var avatarDao: AvatarDao

    @MockK
    lateinit var emojiResponse: Response<Map<String, String>>

    @MockK
    lateinit var avatarResponse: Response<AvatarResponse>

    @InjectMockKs
    lateinit var gitHubRepositoryImpl: GitHubRepositoryImpl

    val responseBody = mapOf( "1" to "First", "2" to "Second", "3" to "third")
    val emojiEntityList = listOf(
        EmojiEntity("1", "First"),
        EmojiEntity("2", "Second"),
        EmojiEntity("3", "third")
    )

    val avatarEntityList = listOf(
        AvatarEntity(1, "avatar1", "avatar1_url"),
        AvatarEntity(2, "avatar2", "avatar2_url"),
        AvatarEntity(3, "avatar3", "avatar3_url")
    )

    val avatarResponseBody = AvatarResponse(
        id = 223156L,
        login = "blissapps",
        avatarUrl = "https://avatars.githubusercontent.com/u/223156?v=4"
    )

    val avatar = avatarResponseBody.toAvatar()

    @Before
    fun setup () {
        MockKAnnotations.init(this)

        coEvery { githubDatasource.listEmojis() } returns emojiResponse
        coEvery { githubDatasource.searchAvatar(any()) } returns avatarResponse
        every { emojiResponse.body() } returns responseBody
        every { avatarResponse.body() } returns avatarResponseBody
        every { emojiDao.insertAll(*anyVararg<EmojiEntity>()) } returns Unit
        every { avatarDao.insertAll(any()) } returns Unit
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

    @Test
    fun testFindAvatar_noLocalData_success() {
        every { avatarDao.findByName(any()) } returns null
        every { avatarResponse.code() } returns 200

        val result = runBlocking {
            gitHubRepositoryImpl.findAvatar("test_username")
        }

        assertNotNull(result)
        assertEquals(223156L, result.id)
        assertEquals("blissapps", result.login)
        assertEquals("https://avatars.githubusercontent.com/u/223156?v=4", result.avatarUrl)
        verify(exactly = 1) { avatarDao.insertAll(any(AvatarEntity::class)) }
    }

    @Test
    fun testFindAvatar_withLocalData_success() {
        every { avatarDao.findByName(any()) } returns avatar.toEntity()

        val result = runBlocking {
            gitHubRepositoryImpl.findAvatar("test_username")
        }

        assertNotNull(result)
        assertEquals(223156L, result.id)
        assertEquals("blissapps", result.login)
        assertEquals("https://avatars.githubusercontent.com/u/223156?v=4", result.avatarUrl)
        coVerify(exactly = 0) { githubDatasource.searchAvatar(any()) }
    }

    @Test
    fun testDeleteAvatar_withLocalData_success() {
        every { avatarDao.delete(any()) } returns Unit

        val result = runBlocking {
            gitHubRepositoryImpl.removeAvatar(avatar)
        }

        assertNotNull(result)
        coVerify(exactly = 1) { avatarDao.delete(any<AvatarEntity>()) }
    }


    @Test
    fun testFindAvatar_noLocalData_parseException() {
        every { avatarDao.findByName(any()) } returns null
        every { avatarResponse.code() } returns 200
        every { avatarResponse.body() } returns null

        Assert.assertThrows(SerializationException::class.java) {
            runBlocking {
                gitHubRepositoryImpl.findAvatar("test_username")
            }
        }
    }

    @Test
    fun testFindAvatar_noLocalData_avatarNotFound() {
        every { avatarDao.findByName(any()) } returns null
        every { avatarResponse.code() } returns 404

        Assert.assertThrows(AvatarNotFoundException::class.java) {
            runBlocking {
                gitHubRepositoryImpl.findAvatar("test_username")
            }
        }
    }

    @Test
    fun testFindAvatar_noLocalData_networkError() {
        every { avatarDao.findByName(any()) } returns null
        every { avatarResponse.code() } returns 500

        Assert.assertThrows(NetworkException::class.java) {
            runBlocking {
                gitHubRepositoryImpl.findAvatar("test_username")
            }
        }
    }

    @Test
    fun testListAvatars_success() {
        every { avatarDao.getAll() } returns avatarEntityList

        val result = runBlocking {
            gitHubRepositoryImpl.getAvatarList()
        }

        assertNotNull(result)
        val avatar1 = result[0]
        assertEquals(1L, avatar1.id)
        assertEquals("avatar1", avatar1.login)
        assertEquals("avatar1_url", avatar1.avatarUrl)
    }
}