// port-lint: source crypto/hash.rs
package io.github.kotlinmania.rustls.crypto

import io.github.kotlinmania.rustls.msgs.HashAlgorithm

/**
 * Describes a single cryptographic hash function.
 *
 * This interface can do both one-shot and incremental hashing, using
 * [Hash.hash] and [Hash.start] respectively.
 */
interface Hash {
    /**
     * Start an incremental hash computation.
     */
    fun start(): Context

    /**
     * Return the output of this hash function with input [data].
     */
    fun hash(data: ByteArray): Output

    /**
     * The length in bytes of this hash function's output.
     */
    fun outputLen(): Int

    /**
     * Which hash function this is, e.g. [HashAlgorithm.SHA256].
     */
    fun algorithm(): HashAlgorithm

    /**
     * Return true if this is backed by a FIPS-approved implementation.
     */
    fun fips(): Boolean = false
}

/**
 * A hash output, stored as a value.
 */
data class Output(
    val bytes: ByteArray,
) {
    init {
        require(bytes.size <= MAX_LEN) { "hash output must not exceed MAX_LEN ($MAX_LEN)" }
    }

    fun asRef(): ByteArray = bytes

    override fun equals(other: Any?): Boolean =
        other is Output && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "Output(bytes.size=${bytes.size})"

    companion object {
        /**
         * Maximum supported hash output size: supports up to SHA512.
         */
        const val MAX_LEN: Int = 64

        fun new(bytes: ByteArray): Output = Output(bytes.copyOf())
    }
}

/**
 * How to incrementally compute a hash.
 */
interface Context {
    /**
     * Finish the computation, returning the resulting output.
     *
     * The computation remains valid, and more data can be added later with [update].
     */
    fun forkFinish(): Output

    /**
     * Fork the computation, producing another context that has the
     * same prefix as this one.
     */
    fun fork(): Context

    /**
     * Terminate and finish the computation, returning the resulting output.
     */
    fun finish(): Output

    /**
     * Add [data] to computation.
     */
    fun update(data: ByteArray)
}
