// port-lint: source msgs/base.rs
package io.github.kotlinmania.rustls.msgs

/**
 * An externally length'd payload.
 */
data class Payload(
    val bytes: ByteArray,
) {
    fun intoVec(): ByteArray = bytes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Payload) return false
        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int = bytes.contentHashCode()

    companion object : Codec<Payload> {
        fun empty(): Payload = Payload(ByteArray(0))

        override fun encode(value: Payload, output: OutputBuffer) {
            output.add(value.bytes)
        }

        override fun read(reader: Reader): Payload = Payload(reader.rest())
    }
}

/**
 * An arbitrary, unknown-content, u24-length-prefixed payload.
 */
data class PayloadU24(
    val payload: Payload,
) {
    constructor(bytes: ByteArray) : this(Payload(bytes))

    fun bytes(): ByteArray = payload.bytes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PayloadU24) return false
        return payload == other.payload
    }

    override fun hashCode(): Int = payload.hashCode()

    companion object : Codec<PayloadU24> {
        override fun encode(value: PayloadU24, output: OutputBuffer) {
            val inner = value.payload.bytes
            U24.encode(U24(inner.size.toUInt()), output)
            output.add(inner)
        }

        override fun read(reader: Reader): PayloadU24 {
            val len = U24.read(reader).value.toInt()
            val sub = reader.sub(len)
            return PayloadU24(Payload(sub.rest()))
        }
    }
}

/**
 * An arbitrary, unknown-content, u16-length-prefixed payload.
 */
data class PayloadU16(
    val bytes: ByteArray,
    val minLen: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PayloadU16) return false
        return bytes.contentEquals(other.bytes) && minLen == other.minLen
    }

    override fun hashCode(): Int = 31 * bytes.contentHashCode() + minLen.hashCode()

    companion object : Codec<PayloadU16> {
        fun empty(): PayloadU16 = PayloadU16(ByteArray(0))

        override fun encode(value: PayloadU16, output: OutputBuffer) {
            CodecU16.encode(value.bytes.size.toUShort(), output)
            output.add(value.bytes)
        }

        override fun read(reader: Reader): PayloadU16 {
            val len = CodecU16.read(reader).toInt()
            val sub = reader.sub(len)
            return PayloadU16(sub.rest())
        }
    }
}

/**
 * An arbitrary, unknown-content, u8-length-prefixed payload.
 */
data class PayloadU8(
    val bytes: ByteArray,
    val minLen: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PayloadU8) return false
        return bytes.contentEquals(other.bytes) && minLen == other.minLen
    }

    override fun hashCode(): Int = 31 * bytes.contentHashCode() + minLen.hashCode()

    companion object : Codec<PayloadU8> {
        fun empty(): PayloadU8 = PayloadU8(ByteArray(0))

        override fun encode(value: PayloadU8, output: OutputBuffer) {
            CodecU8.encode(value.bytes.size.toUByte(), output)
            output.add(value.bytes)
        }

        override fun read(reader: Reader): PayloadU8 {
            val len = CodecU8.read(reader).toInt()
            val sub = reader.sub(len)
            return PayloadU8(sub.rest())
        }
    }
}
