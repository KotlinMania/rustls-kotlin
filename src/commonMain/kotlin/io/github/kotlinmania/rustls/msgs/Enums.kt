// port-lint: source msgs/enums.rs
package io.github.kotlinmania.rustls.msgs

import io.github.kotlinmania.rustls.crypto.KeyExchangeAlgorithm
import io.github.kotlinmania.rustls.crypto.Output

/**
 * The `HashAlgorithm` TLS protocol enum.
 */
public data class HashAlgorithm(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public fun hashForEmptyInput(): Output? =
        when (this) {
            SHA256 ->
                Output.new(
                    byteArrayOf(
                        0xe3.toByte(),
                        0xb0.toByte(),
                        0xc4.toByte(),
                        0x42.toByte(),
                        0x98.toByte(),
                        0xfc.toByte(),
                        0x1c.toByte(),
                        0x14.toByte(),
                        0x9a.toByte(),
                        0xfb.toByte(),
                        0xf4.toByte(),
                        0xc8.toByte(),
                        0x99.toByte(),
                        0x6f.toByte(),
                        0xb9.toByte(),
                        0x24.toByte(),
                        0x27.toByte(),
                        0xae.toByte(),
                        0x41.toByte(),
                        0xe4.toByte(),
                        0x64.toByte(),
                        0x9b.toByte(),
                        0x93.toByte(),
                        0x4c.toByte(),
                        0xa4.toByte(),
                        0x95.toByte(),
                        0x99.toByte(),
                        0x1b.toByte(),
                        0x78.toByte(),
                        0x52.toByte(),
                        0xb8.toByte(),
                        0x55.toByte(),
                    ),
                )
            SHA384 ->
                Output.new(
                    byteArrayOf(
                        0x38.toByte(),
                        0xb0.toByte(),
                        0x60.toByte(),
                        0xa7.toByte(),
                        0x51.toByte(),
                        0xac.toByte(),
                        0x96.toByte(),
                        0x38.toByte(),
                        0x4c.toByte(),
                        0xd9.toByte(),
                        0x32.toByte(),
                        0x7e.toByte(),
                        0xb1.toByte(),
                        0xb1.toByte(),
                        0xe3.toByte(),
                        0x6a.toByte(),
                        0x21.toByte(),
                        0xfd.toByte(),
                        0xb7.toByte(),
                        0x11.toByte(),
                        0x14.toByte(),
                        0xbe.toByte(),
                        0x07.toByte(),
                        0x43.toByte(),
                        0x4c.toByte(),
                        0x0c.toByte(),
                        0xc7.toByte(),
                        0xbf.toByte(),
                        0x63.toByte(),
                        0xf6.toByte(),
                        0xe1.toByte(),
                        0xda.toByte(),
                        0x27.toByte(),
                        0x4e.toByte(),
                        0xde.toByte(),
                        0xbf.toByte(),
                        0xe7.toByte(),
                        0x6f.toByte(),
                        0x65.toByte(),
                        0xfb.toByte(),
                        0xd5.toByte(),
                        0x1a.toByte(),
                        0xd2.toByte(),
                        0xf1.toByte(),
                        0x48.toByte(),
                        0x98.toByte(),
                        0xb8.toByte(),
                        0xfb.toByte(),
                    ),
                )
            SHA512 ->
                Output.new(
                    byteArrayOf(
                        0xcf.toByte(),
                        0x83.toByte(),
                        0xe1.toByte(),
                        0x35.toByte(),
                        0x7e.toByte(),
                        0xef.toByte(),
                        0xb8.toByte(),
                        0xbd.toByte(),
                        0xf1.toByte(),
                        0x54.toByte(),
                        0x28.toByte(),
                        0x50.toByte(),
                        0xd6.toByte(),
                        0x6d.toByte(),
                        0x80.toByte(),
                        0x07.toByte(),
                        0xd6.toByte(),
                        0x20.toByte(),
                        0xe4.toByte(),
                        0x05.toByte(),
                        0x0b.toByte(),
                        0x57.toByte(),
                        0x15.toByte(),
                        0xdc.toByte(),
                        0x83.toByte(),
                        0xf4.toByte(),
                        0xa2.toByte(),
                        0x21.toByte(),
                        0xd7.toByte(),
                        0xe6.toByte(),
                        0x30.toByte(),
                        0x7a.toByte(),
                        0x7b.toByte(),
                        0x48.toByte(),
                        0x85.toByte(),
                        0x8c.toByte(),
                        0x72.toByte(),
                        0xc2.toByte(),
                        0xfc.toByte(),
                        0x2e.toByte(),
                        0xcc.toByte(),
                        0x30.toByte(),
                        0xb3.toByte(),
                        0x5b.toByte(),
                        0x4b.toByte(),
                        0x16.toByte(),
                        0x7e.toByte(),
                        0x97.toByte(),
                        0x85.toByte(),
                        0xb9.toByte(),
                        0x45.toByte(),
                        0xd7.toByte(),
                        0x2f.toByte(),
                        0xd8.toByte(),
                        0xa8.toByte(),
                        0x47.toByte(),
                        0x91.toByte(),
                        0x7f.toByte(),
                        0x4f.toByte(),
                        0x4d.toByte(),
                        0xe4.toByte(),
                        0x95.toByte(),
                        0xa0.toByte(),
                        0x4f.toByte(),
                    ),
                )
            else -> null
        }

    public companion object : Codec<HashAlgorithm> {
        public val NONE: HashAlgorithm = HashAlgorithm(0x00u)
        public val MD5: HashAlgorithm = HashAlgorithm(0x01u)
        public val SHA1: HashAlgorithm = HashAlgorithm(0x02u)
        public val SHA224: HashAlgorithm = HashAlgorithm(0x03u)
        public val SHA256: HashAlgorithm = HashAlgorithm(0x04u)
        public val SHA384: HashAlgorithm = HashAlgorithm(0x05u)
        public val SHA512: HashAlgorithm = HashAlgorithm(0x06u)

        override fun encode(value: HashAlgorithm, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): HashAlgorithm = HashAlgorithm(CodecU8.read(reader))
    }
}

/**
 * The `ClientCertificateType` TLS protocol enum.
 */
public data class ClientCertificateType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<ClientCertificateType> {
        public val RSASign: ClientCertificateType = ClientCertificateType(0x01u)
        public val DSSSign: ClientCertificateType = ClientCertificateType(0x02u)
        public val RSAFixedDH: ClientCertificateType = ClientCertificateType(0x03u)
        public val DSSFixedDH: ClientCertificateType = ClientCertificateType(0x04u)
        public val RSAEphemeralDH: ClientCertificateType = ClientCertificateType(0x05u)
        public val DSSEphemeralDH: ClientCertificateType = ClientCertificateType(0x06u)
        public val ECDSAFixedECDH: ClientCertificateType = ClientCertificateType(0x42u)

        override fun encode(value: ClientCertificateType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): ClientCertificateType = ClientCertificateType(CodecU8.read(reader))
    }
}

/**
 * The `Compression` TLS protocol enum.
 */
public data class Compression(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<Compression> {
        public val Null: Compression = Compression(0x00u)
        public val Deflate: Compression = Compression(0x01u)
        public val LSZ: Compression = Compression(0x40u)

        override fun encode(value: Compression, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): Compression = Compression(CodecU8.read(reader))
    }
}

/**
 * The `AlertLevel` TLS protocol enum.
 */
public data class AlertLevel(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<AlertLevel> {
        public val Warning: AlertLevel = AlertLevel(0x01u)
        public val Fatal: AlertLevel = AlertLevel(0x02u)

        override fun encode(value: AlertLevel, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): AlertLevel = AlertLevel(CodecU8.read(reader))
    }
}

/**
 * The `HeartbeatMessageType` TLS protocol enum.
 */
public data class HeartbeatMessageType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<HeartbeatMessageType> {
        public val Request: HeartbeatMessageType = HeartbeatMessageType(0x01u)
        public val Response: HeartbeatMessageType = HeartbeatMessageType(0x02u)

        override fun encode(value: HeartbeatMessageType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): HeartbeatMessageType = HeartbeatMessageType(CodecU8.read(reader))
    }
}

/**
 * The `ExtensionType` TLS protocol enum.
 */
public data class ExtensionType(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public fun echCompress(): Boolean =
        when (this) {
            StatusRequest,
            EllipticCurves,
            SignatureAlgorithms,
            SignatureAlgorithmsCert,
            ALProtocolNegotiation,
            SupportedVersions,
            Cookie,
            KeyShare,
            PSKKeyExchangeModes,
            -> true
            else -> false
        }

    public companion object : Codec<ExtensionType> {
        public val ServerName: ExtensionType = ExtensionType(0x0000u)
        public val MaxFragmentLength: ExtensionType = ExtensionType(0x0001u)
        public val ClientCertificateURL: ExtensionType = ExtensionType(0x0002u)
        public val TrustedCAKeys: ExtensionType = ExtensionType(0x0003u)
        public val TruncatedHMAC: ExtensionType = ExtensionType(0x0004u)
        public val StatusRequest: ExtensionType = ExtensionType(0x0005u)
        public val UserMapping: ExtensionType = ExtensionType(0x0006u)
        public val ClientAuthz: ExtensionType = ExtensionType(0x0007u)
        public val ServerAuthz: ExtensionType = ExtensionType(0x0008u)
        public val CertType: ExtensionType = ExtensionType(0x0009u)
        public val EllipticCurves: ExtensionType = ExtensionType(0x000au)
        public val ECPointFormats: ExtensionType = ExtensionType(0x000bu)
        public val SRP: ExtensionType = ExtensionType(0x000cu)
        public val SignatureAlgorithms: ExtensionType = ExtensionType(0x000du)
        public val UseSRTP: ExtensionType = ExtensionType(0x000eu)
        public val Heartbeat: ExtensionType = ExtensionType(0x000fu)
        public val ALProtocolNegotiation: ExtensionType = ExtensionType(0x0010u)
        public val StatusRequestv2: ExtensionType = ExtensionType(0x0011u)
        public val SignedCertificateTimestamp: ExtensionType = ExtensionType(0x0012u)
        public val ClientCertificateType: ExtensionType = ExtensionType(0x0013u)
        public val ServerCertificateType: ExtensionType = ExtensionType(0x0014u)
        public val Padding: ExtensionType = ExtensionType(0x0015u)
        public val EncryptThenMAC: ExtensionType = ExtensionType(0x0016u)
        public val ExtendedMasterSecret: ExtensionType = ExtensionType(0x0017u)
        public val CompressCertificate: ExtensionType = ExtensionType(0x001bu)
        public val RecordSizeLimit: ExtensionType = ExtensionType(0x001cu)
        public val SessionTicket: ExtensionType = ExtensionType(0x0023u)
        public val PreSharedKey: ExtensionType = ExtensionType(0x0029u)
        public val EarlyData: ExtensionType = ExtensionType(0x002au)
        public val SupportedVersions: ExtensionType = ExtensionType(0x002bu)
        public val Cookie: ExtensionType = ExtensionType(0x002cu)
        public val PSKKeyExchangeModes: ExtensionType = ExtensionType(0x002du)
        public val CertificateAuthorities: ExtensionType = ExtensionType(0x002fu)
        public val SignatureAlgorithmsCert: ExtensionType = ExtensionType(0x0032u)
        public val KeyShare: ExtensionType = ExtensionType(0x0033u)
        public val TransportParametersDraft: ExtensionType = ExtensionType(0xffa5u)
        public val TransportParameters: ExtensionType = ExtensionType(0x0039u)
        public val EncryptedClientHello: ExtensionType = ExtensionType(0xfe0du)
        public val RenegotiationInfo: ExtensionType = ExtensionType(0xff01u)

        override fun encode(value: ExtensionType, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): ExtensionType = ExtensionType(CodecU16.read(reader))
    }
}

/**
 * The `ServerNameType` TLS protocol enum.
 */
public data class ServerNameType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<ServerNameType> {
        public val HostName: ServerNameType = ServerNameType(0x00u)

        override fun encode(value: ServerNameType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): ServerNameType = ServerNameType(CodecU8.read(reader))
    }
}

/**
 * The `NamedGroup` TLS protocol enum.
 */
public data class NamedGroup(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public fun keyExchangeAlgorithm(): KeyExchangeAlgorithm =
        when (this) {
            secp256r1,
            secp384r1,
            secp521r1,
            X25519,
            X448,
            -> KeyExchangeAlgorithm.ECDHE
            FFDHE2048,
            FFDHE3072,
            FFDHE4096,
            FFDHE6144,
            FFDHE8192,
            -> KeyExchangeAlgorithm.DHE
            else -> KeyExchangeAlgorithm.ECDHE
        }

    public companion object : Codec<NamedGroup> {
        public val secp256r1: NamedGroup = NamedGroup(0x0017u)
        public val secp384r1: NamedGroup = NamedGroup(0x0018u)
        public val secp521r1: NamedGroup = NamedGroup(0x0019u)
        public val X25519: NamedGroup = NamedGroup(0x001Du)
        public val X448: NamedGroup = NamedGroup(0x001Eu)
        public val FFDHE2048: NamedGroup = NamedGroup(0x0100u)
        public val FFDHE3072: NamedGroup = NamedGroup(0x0101u)
        public val FFDHE4096: NamedGroup = NamedGroup(0x0102u)
        public val FFDHE6144: NamedGroup = NamedGroup(0x0103u)
        public val FFDHE8192: NamedGroup = NamedGroup(0x0104u)

        override fun encode(value: NamedGroup, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): NamedGroup = NamedGroup(CodecU16.read(reader))
    }
}

/**
 * The `ECPointFormat` TLS protocol enum.
 */
public data class ECPointFormat(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<ECPointFormat> {
        public val Uncompressed: ECPointFormat = ECPointFormat(0x00u)
        public val ANSIX962CompressedPrime: ECPointFormat = ECPointFormat(0x01u)
        public val ANSIX962CompressedChar2: ECPointFormat = ECPointFormat(0x02u)

        override fun encode(value: ECPointFormat, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): ECPointFormat = ECPointFormat(CodecU8.read(reader))
    }
}

/**
 * The `HeartbeatMode` TLS protocol enum.
 */
public data class HeartbeatMode(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<HeartbeatMode> {
        public val PeerAllowedToSend: HeartbeatMode = HeartbeatMode(0x01u)
        public val PeerNotAllowedToSend: HeartbeatMode = HeartbeatMode(0x02u)

        override fun encode(value: HeartbeatMode, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): HeartbeatMode = HeartbeatMode(CodecU8.read(reader))
    }
}

/**
 * The `ECCurveType` TLS protocol enum.
 */
public data class ECCurveType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<ECCurveType> {
        public val ExplicitPrime: ECCurveType = ECCurveType(0x01u)
        public val ExplicitChar2: ECCurveType = ECCurveType(0x02u)
        public val NamedCurve: ECCurveType = ECCurveType(0x03u)

        override fun encode(value: ECCurveType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): ECCurveType = ECCurveType(CodecU8.read(reader))
    }
}

/**
 * The `PskKeyExchangeMode` TLS protocol enum.
 */
public data class PskKeyExchangeMode(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<PskKeyExchangeMode> {
        public val PSK_KE: PskKeyExchangeMode = PskKeyExchangeMode(0x00u)
        public val PSK_DHE_KE: PskKeyExchangeMode = PskKeyExchangeMode(0x01u)

        override fun encode(value: PskKeyExchangeMode, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): PskKeyExchangeMode = PskKeyExchangeMode(CodecU8.read(reader))
    }
}

/**
 * The `KeyUpdateRequest` TLS protocol enum.
 */
public data class KeyUpdateRequest(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<KeyUpdateRequest> {
        public val UpdateNotRequested: KeyUpdateRequest = KeyUpdateRequest(0x00u)
        public val UpdateRequested: KeyUpdateRequest = KeyUpdateRequest(0x01u)

        override fun encode(value: KeyUpdateRequest, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): KeyUpdateRequest = KeyUpdateRequest(CodecU8.read(reader))
    }
}

/**
 * The `CertificateStatusType` TLS protocol enum.
 */
public data class CertificateStatusType(
    public val value: UByte,
) {
    public constructor(value: Int) : this(value.toUByte())

    public companion object : Codec<CertificateStatusType> {
        public val OCSP: CertificateStatusType = CertificateStatusType(0x01u)

        override fun encode(value: CertificateStatusType, output: OutputBuffer) {
            CodecU8.encode(value.value, output)
        }

        override fun read(reader: Reader): CertificateStatusType = CertificateStatusType(CodecU8.read(reader))
    }
}

/**
 * The `HpkeKem` TLS protocol enum.
 */
public data class HpkeKem(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<HpkeKem> {
        public val DHKEM_P256_HKDF_SHA256: HpkeKem = HpkeKem(0x0010u)
        public val DHKEM_P384_HKDF_SHA384: HpkeKem = HpkeKem(0x0011u)
        public val DHKEM_P521_HKDF_SHA512: HpkeKem = HpkeKem(0x0012u)
        public val DHKEM_X25519_HKDF_SHA256: HpkeKem = HpkeKem(0x0020u)
        public val DHKEM_X448_HKDF_SHA512: HpkeKem = HpkeKem(0x0021u)

        override fun encode(value: HpkeKem, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): HpkeKem = HpkeKem(CodecU16.read(reader))
    }
}

/**
 * The `HpkeKdf` TLS protocol enum.
 */
public data class HpkeKdf(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<HpkeKdf> {
        public val HKDF_SHA256: HpkeKdf = HpkeKdf(0x0001u)
        public val HKDF_SHA384: HpkeKdf = HpkeKdf(0x0002u)
        public val HKDF_SHA512: HpkeKdf = HpkeKdf(0x0003u)

        override fun encode(value: HpkeKdf, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): HpkeKdf = HpkeKdf(CodecU16.read(reader))
    }
}

/**
 * The `HpkeAead` TLS protocol enum.
 */
public data class HpkeAead(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<HpkeAead> {
        public val AES_128_GCM: HpkeAead = HpkeAead(0x0001u)
        public val AES_256_GCM: HpkeAead = HpkeAead(0x0002u)
        public val ChaCha20Poly1305: HpkeAead = HpkeAead(0x0003u)
        public val ExportOnly: HpkeAead = HpkeAead(0xFFFFu)

        override fun encode(value: HpkeAead, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): HpkeAead = HpkeAead(CodecU16.read(reader))
    }
}

/**
 * The `EchVersion` TLS protocol enum.
 */
public data class EchVersion(
    public val value: UShort,
) {
    public constructor(value: Int) : this(value.toUShort())

    public companion object : Codec<EchVersion> {
        public val V18: EchVersion = EchVersion(0xfe0du)

        override fun encode(value: EchVersion, output: OutputBuffer) {
            CodecU16.encode(value.value, output)
        }

        override fun read(reader: Reader): EchVersion = EchVersion(CodecU16.read(reader))
    }
}
