package com.dunamu.jackson.laboratory.serializer

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.SerializerFactory
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy
import com.esotericsoftware.kryo.util.Pool
import org.objenesis.strategy.StdInstantiatorStrategy
import org.springframework.data.redis.serializer.RedisSerializer
import java.io.ByteArrayOutputStream

class KryoRedisSerializer : RedisSerializer<Any?> {

    private val serializer: KryoSerializer = KryoSerializer()

    override fun deserialize(bytes: ByteArray?): Any? {
        return serializer.deserialize(bytes)
    }

    override fun serialize(t: Any?): ByteArray? {
        return serializer.serialize(t)
    }

}

class KryoSerializer {

    private val pool = object : Pool<Kryo>(true, false, 8) {
        override fun create(): Kryo = Kryo().apply {
            classLoader = Thread.currentThread().contextClassLoader
            isRegistrationRequired = false
            (instantiatorStrategy as DefaultInstantiatorStrategy).fallbackInstantiatorStrategy = StdInstantiatorStrategy()
            setDefaultSerializer(SerializerFactory.CompatibleFieldSerializerFactory())
        }
    }

    fun serialize(obj: Any?): ByteArray? = if (obj == null) {
        null
    } else {
        val kryo = pool.obtain()
        try {
            val stream = ByteArrayOutputStream()
            Output(stream).use {
                kryo.writeClassAndObject(it, obj)
            }
            stream.toByteArray()
        } finally {
            pool.free(kryo)
        }
    }

    fun deserialize(bytes: ByteArray?): Any? = if (bytes == null) {
        null
    } else {
        val kryo = pool.obtain()
        try {
            Input(bytes).use {
                kryo.readClassAndObject(it)
            }
        } finally {
            pool.free(kryo)
        }
    }

}