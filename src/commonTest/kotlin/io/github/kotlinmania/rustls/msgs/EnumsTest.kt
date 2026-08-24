// port-lint: tests msgs/enums.rs
package io.github.kotlinmania.rustls.msgs

import io.github.kotlinmania.rustls.crypto.KeyExchangeAlgorithm
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class EnumsTest {
    @Test
    fun testEnums() {
        testEnum8(HashAlgorithm.NONE.value, HashAlgorithm.SHA512.value, HashAlgorithm)
        testEnum8(
            ClientCertificateType.RSASign.value,
            ClientCertificateType.ECDSAFixedECDH.value,
            ClientCertificateType,
        )
        testEnum8(Compression.Null.value, Compression.LSZ.value, Compression)
        testEnum8(AlertLevel.Warning.value, AlertLevel.Fatal.value, AlertLevel)
        testEnum8(HeartbeatMessageType.Request.value, HeartbeatMessageType.Response.value, HeartbeatMessageType)
        testEnum16(ExtensionType.ServerName.value, ExtensionType.RenegotiationInfo.value, ExtensionType)
        testEnum8(ServerNameType.HostName.value, ServerNameType.HostName.value, ServerNameType)
        testEnum16(NamedGroup.secp256r1.value, NamedGroup.FFDHE8192.value, NamedGroup)
        testEnum8(
            ECPointFormat.Uncompressed.value,
            ECPointFormat.ANSIX962CompressedChar2.value,
            ECPointFormat,
        )
        testEnum8(
            HeartbeatMode.PeerAllowedToSend.value,
            HeartbeatMode.PeerNotAllowedToSend.value,
            HeartbeatMode,
        )
        testEnum8(ECCurveType.ExplicitPrime.value, ECCurveType.NamedCurve.value, ECCurveType)
        testEnum8(
            PskKeyExchangeMode.PSK_KE.value,
            PskKeyExchangeMode.PSK_DHE_KE.value,
            PskKeyExchangeMode,
        )
        testEnum8(
            KeyUpdateRequest.UpdateNotRequested.value,
            KeyUpdateRequest.UpdateRequested.value,
            KeyUpdateRequest,
        )
        testEnum8(
            CertificateStatusType.OCSP.value,
            CertificateStatusType.OCSP.value,
            CertificateStatusType,
        )
    }

    @Test
    fun hashForEmptyInput() {
        assertNotNull(HashAlgorithm.SHA256.hashForEmptyInput())
        assertNotNull(HashAlgorithm.SHA384.hashForEmptyInput())
        assertNotNull(HashAlgorithm.SHA512.hashForEmptyInput())
    }

    @Test
    fun testExtensionEchCompress() {
        assertTrue(ExtensionType.StatusRequest.echCompress())
        assertTrue(ExtensionType.EllipticCurves.echCompress())
        assertTrue(ExtensionType.SignatureAlgorithms.echCompress())
        assertTrue(ExtensionType.SignatureAlgorithmsCert.echCompress())
        assertTrue(ExtensionType.ALProtocolNegotiation.echCompress())
        assertTrue(ExtensionType.SupportedVersions.echCompress())
        assertTrue(ExtensionType.Cookie.echCompress())
        assertTrue(ExtensionType.KeyShare.echCompress())
        assertTrue(ExtensionType.PSKKeyExchangeModes.echCompress())

        assertFalse(ExtensionType.ServerName.echCompress())
        assertFalse(ExtensionType.CompressCertificate.echCompress())
    }

    @Test
    fun testNamedGroupKxAlgorithm() {
        assertEquals(KeyExchangeAlgorithm.ECDHE, NamedGroup.secp256r1.keyExchangeAlgorithm())
        assertEquals(KeyExchangeAlgorithm.ECDHE, NamedGroup.X25519.keyExchangeAlgorithm())
        assertEquals(KeyExchangeAlgorithm.DHE, NamedGroup.FFDHE2048.keyExchangeAlgorithm())
        assertEquals(KeyExchangeAlgorithm.DHE, NamedGroup.FFDHE4096.keyExchangeAlgorithm())
    }

    private fun <T> testEnum8(first: UByte, last: UByte, codec: Codec<T>) {
        for (v in first.toInt()..last.toInt()) {
            val bytes = byteArrayOf(v.toByte())
            val decoded = codec.readBytes(bytes)
            val encoded = codec.getEncoding(decoded)
            assertEquals(1, encoded.size)
            assertEquals(v.toByte(), encoded[0])
        }
    }

    private fun <T> testEnum16(first: UShort, last: UShort, codec: Codec<T>) {
        for (v in first.toInt()..last.toInt()) {
            val bytes = byteArrayOf(((v shr 8) and 0xFF).toByte(), (v and 0xFF).toByte())
            val decoded = codec.readBytes(bytes)
            val encoded = codec.getEncoding(decoded)
            assertEquals(2, encoded.size)
            assertEquals(bytes[0], encoded[0])
            assertEquals(bytes[1], encoded[1])
        }
    }
}
