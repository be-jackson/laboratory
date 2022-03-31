package com.dunamu.jackson.laboratory.encrypt

import com.dunamu.jackson.laboratory.configures.EncryptConfigure
import org.assertj.core.api.Assertions.assertThat
import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [EncryptConfigure::class],
    initializers = [ConfigDataApplicationContextInitializer::class]
)
@TestPropertySource(locations = ["/application-test.yml"])
class JasyptStringEncryptorTest {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var stringEncryptor: StringEncryptor

    @Test
    fun `Jasypt 암복호화 테스트`() {
        val plainText = "jackson.lee"
        val encryptText = stringEncryptor.encrypt(plainText)
        val decryptText = stringEncryptor.decrypt(encryptText)
        assertThat(decryptText).isEqualTo(plainText)
        log.info("EncryptText = $encryptText")
    }
}