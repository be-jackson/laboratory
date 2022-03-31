package com.dunamu.jackson.laboratory.encrypt

import com.dunamu.jackson.laboratory.configures.EncryptConfigure
import org.assertj.core.api.Assertions.assertThat
import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
    @Autowired
    lateinit var stringEncryptor: StringEncryptor

    @Test
    fun `Jasypt 복호화 테스트`() {
        assertThat(stringEncryptor.decrypt("cyV4/A0BGtQWv6Ph04OQqw==")).isEqualTo("dive")
        assertThat(stringEncryptor.decrypt("F0KnW6r3V3rxdJWkIY9TjzC2yUdfo4hr")).isEqualTo("divedev123")
    }
}