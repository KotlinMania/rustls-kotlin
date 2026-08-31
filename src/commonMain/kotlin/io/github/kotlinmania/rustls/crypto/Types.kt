// port-lint: source rustls/src/crypto/mod.rs
package io.github.kotlinmania.rustls.crypto

/**
 * Key exchange algorithms used in TLS cipher suites.
 */
enum class KeyExchangeAlgorithm {
    DHE,
    ECDHE,
}

/**
 * Cryptographically secure random number generator interface.
 */
interface SecureRandom {
    fun fill(bytes: ByteArray): Result<Unit>
}

/**
 * Random material generation failed.
 */
class GetRandomFailed : Throwable("random material generation failed") {
    override fun equals(other: Any?): Boolean = other is GetRandomFailed

    override fun hashCode(): Int = "GetRandomFailed".hashCode()

    override fun toString(): String = "GetRandomFailed"
}
