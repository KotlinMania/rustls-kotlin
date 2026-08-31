// port-lint: source rustls/src/enums.rs
package io.github.kotlinmania.rustls

import io.github.kotlinmania.rustls.msgs.Codec
import io.github.kotlinmania.rustls.msgs.CodecU16
import io.github.kotlinmania.rustls.msgs.CodecU8
import io.github.kotlinmania.rustls.msgs.HashAlgorithm
import io.github.kotlinmania.rustls.msgs.OutputBuffer
import io.github.kotlinmania.rustls.msgs.Reader

/**
 * The `AlertDescription` TLS protocol enum.
 */
public data class AlertDescription(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<AlertDescription> {
        public val CloseNotify: AlertDescription = AlertDescription(0x00u)
        public val UnexpectedMessage: AlertDescription = AlertDescription(0x0au)
        public val BadRecordMac: AlertDescription = AlertDescription(0x14u)
        public val DecryptionFailed: AlertDescription = AlertDescription(0x15u)
        public val RecordOverflow: AlertDescription = AlertDescription(0x16u)
        public val DecompressionFailure: AlertDescription = AlertDescription(0x1eu)
        public val HandshakeFailure: AlertDescription = AlertDescription(0x28u)
        public val NoCertificate: AlertDescription = AlertDescription(0x29u)
        public val BadCertificate: AlertDescription = AlertDescription(0x2au)
        public val UnsupportedCertificate: AlertDescription = AlertDescription(0x2bu)
        public val CertificateRevoked: AlertDescription = AlertDescription(0x2cu)
        public val CertificateExpired: AlertDescription = AlertDescription(0x2du)
        public val CertificateUnknown: AlertDescription = AlertDescription(0x2eu)
        public val IllegalParameter: AlertDescription = AlertDescription(0x2fu)
        public val UnknownCA: AlertDescription = AlertDescription(0x30u)
        public val AccessDenied: AlertDescription = AlertDescription(0x31u)
        public val DecodeError: AlertDescription = AlertDescription(0x32u)
        public val DecryptError: AlertDescription = AlertDescription(0x33u)
        public val ExportRestriction: AlertDescription = AlertDescription(0x3cu)
        public val ProtocolVersion: AlertDescription = AlertDescription(0x46u)
        public val InsufficientSecurity: AlertDescription = AlertDescription(0x47u)
        public val InternalError: AlertDescription = AlertDescription(0x50u)
        public val InappropriateFallback: AlertDescription = AlertDescription(0x56u)
        public val UserCanceled: AlertDescription = AlertDescription(0x5au)
        public val NoRenegotiation: AlertDescription = AlertDescription(0x64u)
        public val MissingExtension: AlertDescription = AlertDescription(0x6du)
        public val UnsupportedExtension: AlertDescription = AlertDescription(0x6eu)
        public val CertificateUnobtainable: AlertDescription = AlertDescription(0x6fu)
        public val UnrecognisedName: AlertDescription = AlertDescription(0x70u)
        public val BadCertificateStatusResponse: AlertDescription = AlertDescription(0x71u)
        public val BadCertificateHashValue: AlertDescription = AlertDescription(0x72u)
        public val UnknownPSKIdentity: AlertDescription = AlertDescription(0x73u)
        public val CertificateRequired: AlertDescription = AlertDescription(0x74u)
        public val NoApplicationProtocol: AlertDescription = AlertDescription(0x78u)
        public val EncryptedClientHelloRequired: AlertDescription = AlertDescription(0x79u)

        override fun encode(value: AlertDescription, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): AlertDescription = AlertDescription(CodecU8.read(reader))
    }
}

/**
 * The `HandshakeType` TLS protocol enum.
 */
public data class HandshakeType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<HandshakeType> {
        public val HelloRequest: HandshakeType = HandshakeType(0x00u)
        public val ClientHello: HandshakeType = HandshakeType(0x01u)
        public val ServerHello: HandshakeType = HandshakeType(0x02u)
        public val HelloVerifyRequest: HandshakeType = HandshakeType(0x03u)
        public val NewSessionTicket: HandshakeType = HandshakeType(0x04u)
        public val EndOfEarlyData: HandshakeType = HandshakeType(0x05u)
        public val HelloRetryRequest: HandshakeType = HandshakeType(0x06u)
        public val EncryptedExtensions: HandshakeType = HandshakeType(0x08u)
        public val Certificate: HandshakeType = HandshakeType(0x0bu)
        public val ServerKeyExchange: HandshakeType = HandshakeType(0x0cu)
        public val CertificateRequest: HandshakeType = HandshakeType(0x0du)
        public val ServerHelloDone: HandshakeType = HandshakeType(0x0eu)
        public val CertificateVerify: HandshakeType = HandshakeType(0x0fu)
        public val ClientKeyExchange: HandshakeType = HandshakeType(0x10u)
        public val Finished: HandshakeType = HandshakeType(0x14u)
        public val CertificateURL: HandshakeType = HandshakeType(0x15u)
        public val CertificateStatus: HandshakeType = HandshakeType(0x16u)
        public val KeyUpdate: HandshakeType = HandshakeType(0x18u)
        public val CompressedCertificate: HandshakeType = HandshakeType(0x19u)
        public val MessageHash: HandshakeType = HandshakeType(0xfeu)

        override fun encode(value: HandshakeType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): HandshakeType = HandshakeType(CodecU8.read(reader))
    }
}

/**
 * The `ContentType` TLS protocol enum.
 */
public data class ContentType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<ContentType> {
        public val ChangeCipherSpec: ContentType = ContentType(0x14u)
        public val Alert: ContentType = ContentType(0x15u)
        public val Handshake: ContentType = ContentType(0x16u)
        public val ApplicationData: ContentType = ContentType(0x17u)
        public val Heartbeat: ContentType = ContentType(0x18u)

        override fun encode(value: ContentType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): ContentType = ContentType(CodecU8.read(reader))
    }
}

/**
 * The `ProtocolVersion` TLS protocol enum.
 */
public data class ProtocolVersion(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<ProtocolVersion> {
        public val SSLv2: ProtocolVersion = ProtocolVersion(0x0002u)
        public val SSLv3: ProtocolVersion = ProtocolVersion(0x0300u)
        public val TLSv1_0: ProtocolVersion = ProtocolVersion(0x0301u)
        public val TLSv1_1: ProtocolVersion = ProtocolVersion(0x0302u)
        public val TLSv1_2: ProtocolVersion = ProtocolVersion(0x0303u)
        public val TLSv1_3: ProtocolVersion = ProtocolVersion(0x0304u)
        public val DTLSv1_0: ProtocolVersion = ProtocolVersion(0xFEFFu)
        public val DTLSv1_2: ProtocolVersion = ProtocolVersion(0xFEFDu)
        public val DTLSv1_3: ProtocolVersion = ProtocolVersion(0xFEFCu)

        override fun encode(value: ProtocolVersion, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): ProtocolVersion = ProtocolVersion(CodecU16.read(reader))
    }
}

/**
 * The `SignatureAlgorithm` TLS protocol enum.
 */
public data class SignatureAlgorithm(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<SignatureAlgorithm> {
        public val Anonymous: SignatureAlgorithm = SignatureAlgorithm(0x00u)
        public val RSA: SignatureAlgorithm = SignatureAlgorithm(0x01u)
        public val DSA: SignatureAlgorithm = SignatureAlgorithm(0x02u)
        public val ECDSA: SignatureAlgorithm = SignatureAlgorithm(0x03u)
        public val ED25519: SignatureAlgorithm = SignatureAlgorithm(0x07u)
        public val ED448: SignatureAlgorithm = SignatureAlgorithm(0x08u)

        override fun encode(value: SignatureAlgorithm, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): SignatureAlgorithm = SignatureAlgorithm(CodecU8.read(reader))
    }
}

/**
 * The `SignatureScheme` TLS protocol enum.
 */
public data class SignatureScheme(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public fun algorithm(): SignatureAlgorithm =
        when (this) {
            RSA_PKCS1_SHA1,
            RSA_PKCS1_SHA256,
            RSA_PKCS1_SHA384,
            RSA_PKCS1_SHA512,
            RSA_PSS_SHA256,
            RSA_PSS_SHA384,
            RSA_PSS_SHA512,
            -> SignatureAlgorithm.RSA
            ECDSA_SHA1_Legacy,
            ECDSA_NISTP256_SHA256,
            ECDSA_NISTP384_SHA384,
            ECDSA_NISTP521_SHA512,
            -> SignatureAlgorithm.ECDSA
            ED25519 -> SignatureAlgorithm.ED25519
            ED448 -> SignatureAlgorithm.ED448
            else -> SignatureAlgorithm(0u)
        }

    public fun supportedInTls13(): Boolean {
        val hashVal = ((value.toInt() shr 8) and 0xFF).toUByte()
        val signVal = (value.toInt() and 0xFF).toUByte()

        val hash = HashAlgorithm(hashVal)
        if (hash == HashAlgorithm.NONE ||
            hash == HashAlgorithm.MD5 ||
            hash == HashAlgorithm.SHA1 ||
            hash == HashAlgorithm.SHA224
        ) {
            return false
        }

        val sign = SignatureAlgorithm(signVal)
        return sign != SignatureAlgorithm.Anonymous &&
            sign != SignatureAlgorithm.RSA &&
            sign != SignatureAlgorithm.DSA
    }

    public companion object : Codec<SignatureScheme> {
        public val RSA_PKCS1_SHA1: SignatureScheme = SignatureScheme(0x0201u)
        public val ECDSA_SHA1_Legacy: SignatureScheme = SignatureScheme(0x0203u)
        public val RSA_PKCS1_SHA256: SignatureScheme = SignatureScheme(0x0401u)
        public val ECDSA_NISTP256_SHA256: SignatureScheme = SignatureScheme(0x0403u)
        public val RSA_PKCS1_SHA384: SignatureScheme = SignatureScheme(0x0501u)
        public val ECDSA_NISTP384_SHA384: SignatureScheme = SignatureScheme(0x0503u)
        public val RSA_PKCS1_SHA512: SignatureScheme = SignatureScheme(0x0601u)
        public val ECDSA_NISTP521_SHA512: SignatureScheme = SignatureScheme(0x0603u)
        public val RSA_PSS_SHA256: SignatureScheme = SignatureScheme(0x0804u)
        public val RSA_PSS_SHA384: SignatureScheme = SignatureScheme(0x0805u)
        public val RSA_PSS_SHA512: SignatureScheme = SignatureScheme(0x0806u)
        public val ED25519: SignatureScheme = SignatureScheme(0x0807u)
        public val ED448: SignatureScheme = SignatureScheme(0x0808u)
        public val ML_DSA_44: SignatureScheme = SignatureScheme(0x0904u)
        public val ML_DSA_65: SignatureScheme = SignatureScheme(0x0905u)
        public val ML_DSA_87: SignatureScheme = SignatureScheme(0x0906u)

        override fun encode(value: SignatureScheme, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): SignatureScheme = SignatureScheme(CodecU16.read(reader))
    }
}

/**
 * The `CipherSuite` TLS protocol enum.
 */
public data class CipherSuite(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<CipherSuite> {
        public val TLS_NULL_WITH_NULL_NULL: CipherSuite = CipherSuite(0x0000u)
        public val TLS_PSK_WITH_AES_128_GCM_SHA256: CipherSuite = CipherSuite(0x00a8u)
        public val TLS_PSK_WITH_AES_256_GCM_SHA384: CipherSuite = CipherSuite(0x00a9u)
        public val TLS_EMPTY_RENEGOTIATION_INFO_SCSV: CipherSuite = CipherSuite(0x00ffu)
        public val TLS13_AES_128_GCM_SHA256: CipherSuite = CipherSuite(0x1301u)
        public val TLS13_AES_256_GCM_SHA384: CipherSuite = CipherSuite(0x1302u)
        public val TLS13_CHACHA20_POLY1305_SHA256: CipherSuite = CipherSuite(0x1303u)
        public val TLS13_AES_128_CCM_SHA256: CipherSuite = CipherSuite(0x1304u)
        public val TLS13_AES_128_CCM_8_SHA256: CipherSuite = CipherSuite(0x1305u)
        public val TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA: CipherSuite = CipherSuite(0xc009u)
        public val TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA: CipherSuite = CipherSuite(0xc00au)
        public val TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA: CipherSuite = CipherSuite(0xc013u)
        public val TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA: CipherSuite = CipherSuite(0xc014u)
        public val TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256: CipherSuite = CipherSuite(0xc023u)
        public val TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384: CipherSuite = CipherSuite(0xc024u)
        public val TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256: CipherSuite = CipherSuite(0xc027u)
        public val TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384: CipherSuite = CipherSuite(0xc028u)
        public val TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256: CipherSuite = CipherSuite(0xc02bu)
        public val TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384: CipherSuite = CipherSuite(0xc02cu)
        public val TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256: CipherSuite = CipherSuite(0xc02fu)
        public val TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384: CipherSuite = CipherSuite(0xc030u)
        public val TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256: CipherSuite = CipherSuite(0xcca8u)
        public val TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256: CipherSuite = CipherSuite(0xcca9u)

        override fun encode(value: CipherSuite, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): CipherSuite = CipherSuite(CodecU16.read(reader))
    }
}

/**
 * The "TLS Certificate Compression Algorithm IDs" TLS protocol enum.
 */
public data class CertificateCompressionAlgorithm(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<CertificateCompressionAlgorithm> {
        public val Zlib: CertificateCompressionAlgorithm = CertificateCompressionAlgorithm(1u)
        public val Brotli: CertificateCompressionAlgorithm = CertificateCompressionAlgorithm(2u)
        public val Zstd: CertificateCompressionAlgorithm = CertificateCompressionAlgorithm(3u)

        override fun encode(value: CertificateCompressionAlgorithm, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): CertificateCompressionAlgorithm = CertificateCompressionAlgorithm(CodecU16.read(reader))
    }
}

/**
 * The `CertificateType` enum sent in the cert_type extensions.
 */
public data class CertificateType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<CertificateType> {
        public val X509: CertificateType = CertificateType(0x00u)
        public val RawPublicKey: CertificateType = CertificateType(0x02u)

        override fun encode(value: CertificateType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): CertificateType = CertificateType(CodecU8.read(reader))
    }
}

/**
 * The type of Encrypted Client Hello (`EchClientHelloType`).
 */
public data class EchClientHelloType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<EchClientHelloType> {
        public val ClientHelloOuter: EchClientHelloType = EchClientHelloType(0u)
        public val ClientHelloInner: EchClientHelloType = EchClientHelloType(1u)

        override fun encode(value: EchClientHelloType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): EchClientHelloType = EchClientHelloType(CodecU8.read(reader))
    }
}
