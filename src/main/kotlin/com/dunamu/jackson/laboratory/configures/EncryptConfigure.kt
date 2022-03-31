package com.dunamu.jackson.laboratory.configures

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "jasypt.encryptor")
class EncryptProperties {
    var password: String = ""
}

@Configuration
@EnableConfigurationProperties(EncryptProperties::class)
class EncryptConfigure {

    @Bean
    fun jasyptStringEncryptor(
        properties: EncryptProperties
    ): StringEncryptor = PooledPBEStringEncryptor().apply {
        setConfig(
            SimpleStringPBEConfig().apply {
                password = properties.password
                algorithm = "PBEWithMD5AndDES"
                keyObtentionIterations = 1000
                poolSize = 1
                setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
                stringOutputType = "base64"
            }
        )
    }

}