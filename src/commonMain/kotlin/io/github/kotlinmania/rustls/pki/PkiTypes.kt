// port-lint: source lib.rs
package io.github.kotlinmania.rustls.pki

import kotlin.time.Duration

/**
 * An exact time in seconds since the Unix epoch (00:00:00 UTC on 1 January 1970).
 */
data class UnixTime(
    val epochSeconds: Long,
) : Comparable<UnixTime> {
    fun asSecs(): Long = epochSeconds

    fun saturatingSub(other: UnixTime): Long =
        if (epochSeconds > other.epochSeconds) {
            epochSeconds - other.epochSeconds
        } else {
            0L
        }

    override fun compareTo(other: UnixTime): Int = epochSeconds.compareTo(other.epochSeconds)

    override fun toString(): String = "UnixTime($epochSeconds)"

    companion object {
        fun sinceUnixEpoch(duration: Duration): UnixTime = UnixTime(duration.inWholeSeconds)

        fun fromUniversalTime(
            year: Int,
            month: Int,
            day: Int,
            hour: Int,
            minute: Int,
            second: Int,
        ): UnixTime {
            // Basic civil date to epoch seconds conversion
            var y = year
            var m = month
            if (m <= 2) {
                y -= 1
                m += 12
            }
            val era = (if (y >= 0) y else y - 399) / 400
            val yoe = y - era * 400
            val doy = (153 * (m + 2) + 2) / 5 + day - 1
            val doe = yoe * 365 + yoe / 4 - yoe / 100 + doy
            val days = era * 146097L + doe - 719468L
            val secs = days * 86400L + hour * 3600L + minute * 60L + second
            return UnixTime(secs)
        }

        fun now(): UnixTime =
            UnixTime(
                kotlin.time.TimeSource.Monotonic
                    .markNow()
                    .elapsedNow()
                    .inWholeSeconds,
            )
    }
}

/**
 * A DER-encoded X.509 certificate.
 */
data class CertificateDer(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is CertificateDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "CertificateDer(bytes.size=${bytes.size})"
}

/**
 * A DER-encoded Certificate Revocation List (CRL).
 */
data class CertificateRevocationListDer(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is CertificateRevocationListDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "CertificateRevocationListDer(bytes.size=${bytes.size})"
}

/**
 * A DER-encoded SubjectPublicKeyInfo structure.
 */
data class SubjectPublicKeyInfoDer(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is SubjectPublicKeyInfoDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "SubjectPublicKeyInfoDer(bytes.size=${bytes.size})"
}

/**
 * A DER-encoded private key in PKCS#1 format.
 */
data class PrivatePkcs1KeyDer(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is PrivatePkcs1KeyDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()
}

/**
 * A DER-encoded private key in SEC1 format.
 */
data class PrivateSec1KeyDer(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is PrivateSec1KeyDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()
}

/**
 * A DER-encoded private key in PKCS#8 format.
 */
data class PrivatePkcs8KeyDer(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is PrivatePkcs8KeyDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()
}

/**
 * An enumerated private key container supporting PKCS#8, PKCS#1, or SEC1 encodings.
 */
sealed class PrivateKeyDer {
    data class Pkcs8(
        val key: PrivatePkcs8KeyDer,
    ) : PrivateKeyDer()

    data class Pkcs1(
        val key: PrivatePkcs1KeyDer,
    ) : PrivateKeyDer()

    data class Sec1(
        val key: PrivateSec1KeyDer,
    ) : PrivateKeyDer()
}

/**
 * A trust anchor used for validating certificate chains.
 */
data class TrustAnchor(
    val subject: ByteArray,
    val spki: ByteArray,
    val nameConstraints: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TrustAnchor) return false
        if (!subject.contentEquals(other.subject)) return false
        if (!spki.contentEquals(other.spki)) return false
        return if (nameConstraints != null) {
            other.nameConstraints != null && nameConstraints.contentEquals(other.nameConstraints)
        } else {
            other.nameConstraints == null
        }
    }

    override fun hashCode(): Int {
        var result = subject.contentHashCode()
        result = 31 * result + spki.contentHashCode()
        result = 31 * result + (nameConstraints?.contentHashCode() ?: 0)
        return result
    }
}

/**
 * ASN.1 AlgorithmIdentifier encoded in DER.
 */
data class AlgorithmIdentifier(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is AlgorithmIdentifier && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "AlgorithmIdentifier(bytes.size=${bytes.size})"
}

/**
 * Encrypted Client Hello configuration list bytes.
 */
data class EchConfigListBytes(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is EchConfigListBytes && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()
}

/**
 * Generic DER-encoded data holder.
 */
data class Der(
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is Der && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()
}

/**
 * FIPS compatibility status.
 */
enum class FipsStatus {
    Disabled,
    Enabled,
}

/**
 * Error indicating signature verification failed.
 */
object InvalidSignature : Throwable("invalid signature")

/**
 * Trait for verifying cryptographic signatures against public keys.
 */
interface SignatureVerificationAlgorithm {
    fun verifySignature(
        publicKey: ByteArray,
        message: ByteArray,
        signature: ByteArray,
    ): Result<Unit>
}

/**
 * Server name indication (SNI) types: DNS name or IP address.
 */
sealed class ServerName {
    data class DnsNameValue(
        val dnsName: DnsName,
    ) : ServerName()

    data class IpAddressValue(
        val ipAddress: IpAddr,
    ) : ServerName()

    fun toOwned(): ServerName =
        when (this) {
            is DnsNameValue -> DnsNameValue(dnsName.toOwned())
            is IpAddressValue -> this
        }

    fun toStr(): String =
        when (this) {
            is DnsNameValue -> dnsName.asRef()
            is IpAddressValue -> ipAddress.toString()
        }

    companion object {
        fun tryFrom(value: String): Result<ServerName> {
            val dnsResult = DnsName.tryFrom(value)
            if (dnsResult.isSuccess) {
                return Result.success(DnsNameValue(dnsResult.getOrThrow()))
            }
            val ipResult = IpAddr.tryFrom(value)
            if (ipResult.isSuccess) {
                return Result.success(IpAddressValue(ipResult.getOrThrow()))
            }
            return Result.failure(InvalidDnsNameError)
        }

        fun from(addr: IpAddr): ServerName = IpAddressValue(addr)

        fun from(v4: Ipv4Addr): ServerName = IpAddressValue(IpAddr.V4(v4))

        fun from(v6: Ipv6Addr): ServerName = IpAddressValue(IpAddr.V6(v6))

        fun from(dnsName: DnsName): ServerName = DnsNameValue(dnsName)
    }
}

/**
 * Encapsulates a syntactically valid DNS name string.
 */
class DnsName private constructor(
    val value: String,
) {
    fun toOwned(): DnsName = DnsName(value)

    fun toLowercaseOwned(): DnsName = DnsName(value.lowercase())

    fun asRef(): String = value

    override fun equals(other: Any?): Boolean =
        other is DnsName && value.equals(other.value, ignoreCase = true)

    override fun hashCode(): Int = value.lowercase().hashCode()

    override fun toString(): String = "DnsName(\"$value\")"

    companion object {
        fun tryFromString(s: String): Result<DnsName> =
            if (validate(s.encodeToByteArray())) {
                Result.success(DnsName(s))
            } else {
                Result.failure(InvalidDnsNameError)
            }

        fun tryFrom(s: String): Result<DnsName> = tryFromString(s)

        fun tryFrom(value: ByteArray): Result<DnsName> {
            val s = value.decodeToString()
            return if (validate(value)) {
                Result.success(DnsName(s))
            } else {
                Result.failure(InvalidDnsNameError)
            }
        }
    }
}

object InvalidDnsNameError : Throwable("invalid dns name")

private enum class DnsValidationState {
    Start,
    Next,
    NumericOnly,
    NextAfterNumericOnly,
    Subsequent,
    Hyphen,
}

private const val MAX_LABEL_LENGTH: Int = 63
private const val MAX_NAME_LENGTH: Int = 253

private fun validate(input: ByteArray): Boolean {
    var state = DnsValidationState.Start
    var len = 0

    if (input.isEmpty() || input.size > MAX_NAME_LENGTH) {
        return false
    }

    var idx = 0
    while (idx < input.size) {
        val ch = input[idx].toInt() and 0xff
        state =
            when (state) {
                DnsValidationState.Start, DnsValidationState.Next, DnsValidationState.NextAfterNumericOnly -> {
                    when (ch) {
                        '.'.code -> return false
                        in '0'.code..'9'.code -> {
                            len = 1
                            DnsValidationState.NumericOnly
                        }
                        in 'a'.code..'z'.code, in 'A'.code..'Z'.code, '_'.code -> {
                            len = 1
                            DnsValidationState.Subsequent
                        }
                        else -> return false
                    }
                }
                DnsValidationState.Subsequent -> {
                    when (ch) {
                        '.'.code -> DnsValidationState.Next
                        '-'.code -> {
                            len += 1
                            DnsValidationState.Hyphen
                        }
                        in 'a'.code..'z'.code, in 'A'.code..'Z'.code, '_'.code, in '0'.code..'9'.code -> {
                            len += 1
                            DnsValidationState.Subsequent
                        }
                        else -> return false
                    }
                }
                DnsValidationState.NumericOnly -> {
                    when (ch) {
                        '.'.code -> DnsValidationState.NextAfterNumericOnly
                        '-'.code -> {
                            len += 1
                            DnsValidationState.Hyphen
                        }
                        in 'a'.code..'z'.code, in 'A'.code..'Z'.code, '_'.code, in '0'.code..'9'.code -> {
                            len += 1
                            DnsValidationState.Subsequent
                        }
                        else -> return false
                    }
                }
                DnsValidationState.Hyphen -> {
                    when {
                        ch == '.'.code -> return false
                        len >= MAX_LABEL_LENGTH -> return false
                        '-'.code == ch -> {
                            len += 1
                            DnsValidationState.Hyphen
                        }
                        (
                            ch in 'a'.code..'z'.code ||
                                ch in 'A'.code..'Z'.code ||
                                ch == '_'.code ||
                                ch in '0'.code..'9'.code
                        ) -> {
                            len += 1
                            DnsValidationState.Subsequent
                        }
                        else -> return false
                    }
                }
            }

        when (state) {
            DnsValidationState.Subsequent, DnsValidationState.NumericOnly, DnsValidationState.Hyphen -> {
                if (len > MAX_LABEL_LENGTH) return false
            }
            else -> {}
        }

        idx++
    }

    return when (state) {
        DnsValidationState.Start, DnsValidationState.Hyphen,
        DnsValidationState.NumericOnly, DnsValidationState.NextAfterNumericOnly,
        -> false
        else -> true
    }
}

/**
 * An IP address: IPv4 or IPv6.
 */
sealed class IpAddr {
    class V4(
        val addr: Ipv4Addr,
    ) : IpAddr() {
        override fun equals(other: Any?): Boolean = other is V4 && addr == other.addr

        override fun hashCode(): Int = addr.hashCode()

        override fun toString(): String = addr.toString()
    }

    class V6(
        val addr: Ipv6Addr,
    ) : IpAddr() {
        override fun equals(other: Any?): Boolean = other is V6 && addr == other.addr

        override fun hashCode(): Int = addr.hashCode()

        override fun toString(): String = addr.toString()
    }

    companion object {
        fun tryFrom(value: String): Result<IpAddr> {
            val v4Result = Ipv4Addr.tryFrom(value)
            if (v4Result.isSuccess) {
                return Result.success(V4(v4Result.getOrThrow()))
            }
            return Ipv6Addr.tryFrom(value).map { V6(it) }
        }

        fun from(v4: Ipv4Addr): IpAddr = V4(v4)

        fun from(v6: Ipv6Addr): IpAddr = V6(v6)
    }

    override fun toString(): String =
        when (this) {
            is V4 -> addr.toString()
            is V6 -> addr.toString()
        }
}

/**
 * An IPv4 address (4 octets).
 */
data class Ipv4Addr(
    val octets: ByteArray,
) {
    init {
        require(octets.size == 4) { "IPv4 address must have 4 octets" }
    }

    companion object {
        fun from(value: ByteArray): Ipv4Addr = Ipv4Addr(value)

        fun tryFrom(value: String): Result<Ipv4Addr> {
            val parts = value.split('.')
            if (parts.size != 4) {
                return Result.failure(AddrParseError(AddrKind.Ipv4))
            }
            val bytes = ByteArray(4)
            for (i in 0..3) {
                val p = parts[i]
                if (p.isEmpty() || (p.length > 1 && p.startsWith('0'))) {
                    return Result.failure(AddrParseError(AddrKind.Ipv4))
                }
                val n = p.toIntOrNull() ?: return Result.failure(AddrParseError(AddrKind.Ipv4))
                if (n !in 0..255) return Result.failure(AddrParseError(AddrKind.Ipv4))
                bytes[i] = n.toByte()
            }
            return Result.success(Ipv4Addr(bytes))
        }
    }

    override fun equals(other: Any?): Boolean = other is Ipv4Addr && octets.contentEquals(other.octets)

    override fun hashCode(): Int = octets.contentHashCode()

    override fun toString(): String = octets.joinToString(".") { (it.toInt() and 0xff).toString() }
}

/**
 * An IPv6 address (16 octets).
 */
data class Ipv6Addr(
    val octets: ByteArray,
) {
    init {
        require(octets.size == 16) { "IPv6 address must have 16 octets" }
    }

    companion object {
        fun from(value: ByteArray): Ipv6Addr = Ipv6Addr(value)

        fun tryFrom(value: String): Result<Ipv6Addr> {
            if (value.isEmpty()) return Result.failure(AddrParseError(AddrKind.Ipv6))
            val hasDoubleColon = value.contains("::")
            if (hasDoubleColon && value.indexOf("::") != value.lastIndexOf("::")) {
                return Result.failure(AddrParseError(AddrKind.Ipv6))
            }
            val segments = value.split(":")
            val hexParts = mutableListOf<Int>()
            // Parsing simplified
            var idx = 0
            while (idx < segments.size) {
                val seg = segments[idx]
                if (seg.isNotEmpty()) {
                    val v = seg.toIntOrNull(16) ?: return Result.failure(AddrParseError(AddrKind.Ipv6))
                    if (v !in 0..0xffff) return Result.failure(AddrParseError(AddrKind.Ipv6))
                    hexParts.add(v)
                }
                idx++
            }
            if (hexParts.size > 8) return Result.failure(AddrKind.Ipv6.let { AddrParseError(it) })
            val bytes = ByteArray(16)
            for (i in hexParts.indices) {
                bytes[i * 2] = ((hexParts[i] ushr 8) and 0xff).toByte()
                bytes[i * 2 + 1] = (hexParts[i] and 0xff).toByte()
            }
            return Result.success(Ipv6Addr(bytes))
        }
    }

    override fun equals(other: Any?): Boolean = other is Ipv6Addr && octets.contentEquals(other.octets)

    override fun hashCode(): Int = octets.contentHashCode()
}

enum class AddrKind { Ipv4, Ipv6 }

data class AddrParseError(
    val kind: AddrKind,
) : Throwable(
        when (kind) {
            AddrKind.Ipv4 -> "invalid IPv4 address syntax"
            AddrKind.Ipv6 -> "invalid IPv6 address syntax"
        },
    )
