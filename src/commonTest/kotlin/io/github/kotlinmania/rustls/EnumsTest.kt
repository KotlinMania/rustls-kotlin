// port-lint: tests enums.rs
package io.github.kotlinmania.rustls

import io.github.kotlinmania.rustls.msgs.Reader
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EnumsTest {
    @Test
    fun testEnums() {
        val signatureAlgorithms =
            listOf(
                SignatureAlgorithm.Anonymous,
                SignatureAlgorithm.RSA,
                SignatureAlgorithm.DSA,
                SignatureAlgorithm.ECDSA,
                SignatureAlgorithm.ED25519,
                SignatureAlgorithm.ED448,
            )
        for (item in signatureAlgorithms) {
            val encoded = SignatureAlgorithm.getEncoding(item)
            assertEquals(1, encoded.size)
            val decoded = SignatureAlgorithm.read(Reader(encoded))
            assertEquals(item, decoded)
        }

        val contentTypes =
            listOf(
                ContentType.ChangeCipherSpec,
                ContentType.Alert,
                ContentType.Handshake,
                ContentType.ApplicationData,
                ContentType.Heartbeat,
            )
        for (item in contentTypes) {
            val encoded = ContentType.getEncoding(item)
            assertEquals(1, encoded.size)
            val decoded = ContentType.read(Reader(encoded))
            assertEquals(item, decoded)
        }

        val handshakeTypes =
            listOf(
                HandshakeType.HelloRequest,
                HandshakeType.ClientHello,
                HandshakeType.ServerHello,
                HandshakeType.HelloVerifyRequest,
                HandshakeType.NewSessionTicket,
                HandshakeType.EndOfEarlyData,
                HandshakeType.HelloRetryRequest,
                HandshakeType.EncryptedExtensions,
                HandshakeType.Certificate,
                HandshakeType.ServerKeyExchange,
                HandshakeType.CertificateRequest,
                HandshakeType.ServerHelloDone,
                HandshakeType.CertificateVerify,
                HandshakeType.ClientKeyExchange,
                HandshakeType.Finished,
                HandshakeType.CertificateURL,
                HandshakeType.CertificateStatus,
                HandshakeType.KeyUpdate,
                HandshakeType.CompressedCertificate,
                HandshakeType.MessageHash,
            )
        for (item in handshakeTypes) {
            val encoded = HandshakeType.getEncoding(item)
            assertEquals(1, encoded.size)
            val decoded = HandshakeType.read(Reader(encoded))
            assertEquals(item, decoded)
        }

        val alertDescriptions =
            listOf(
                AlertDescription.CloseNotify,
                AlertDescription.UnexpectedMessage,
                AlertDescription.BadRecordMac,
                AlertDescription.DecryptionFailed,
                AlertDescription.RecordOverflow,
                AlertDescription.DecompressionFailure,
                AlertDescription.HandshakeFailure,
                AlertDescription.NoCertificate,
                AlertDescription.BadCertificate,
                AlertDescription.UnsupportedCertificate,
                AlertDescription.CertificateRevoked,
                AlertDescription.CertificateExpired,
                AlertDescription.CertificateUnknown,
                AlertDescription.IllegalParameter,
                AlertDescription.UnknownCA,
                AlertDescription.AccessDenied,
                AlertDescription.DecodeError,
                AlertDescription.DecryptError,
                AlertDescription.ExportRestriction,
                AlertDescription.ProtocolVersion,
                AlertDescription.InsufficientSecurity,
                AlertDescription.InternalError,
                AlertDescription.InappropriateFallback,
                AlertDescription.UserCanceled,
                AlertDescription.NoRenegotiation,
                AlertDescription.MissingExtension,
                AlertDescription.UnsupportedExtension,
                AlertDescription.CertificateUnobtainable,
                AlertDescription.UnrecognisedName,
                AlertDescription.BadCertificateStatusResponse,
                AlertDescription.BadCertificateHashValue,
                AlertDescription.UnknownPSKIdentity,
                AlertDescription.CertificateRequired,
                AlertDescription.NoApplicationProtocol,
                AlertDescription.EncryptedClientHelloRequired,
            )
        for (item in alertDescriptions) {
            val encoded = AlertDescription.getEncoding(item)
            assertEquals(1, encoded.size)
            val decoded = AlertDescription.read(Reader(encoded))
            assertEquals(item, decoded)
        }

        val certCompAlgs =
            listOf(
                CertificateCompressionAlgorithm.Zlib,
                CertificateCompressionAlgorithm.Brotli,
                CertificateCompressionAlgorithm.Zstd,
            )
        for (item in certCompAlgs) {
            val encoded = CertificateCompressionAlgorithm.getEncoding(item)
            assertEquals(2, encoded.size)
            val decoded = CertificateCompressionAlgorithm.read(Reader(encoded))
            assertEquals(item, decoded)
        }

        val certTypes =
            listOf(
                CertificateType.X509,
                CertificateType.RawPublicKey,
            )
        for (item in certTypes) {
            val encoded = CertificateType.getEncoding(item)
            assertEquals(1, encoded.size)
            val decoded = CertificateType.read(Reader(encoded))
            assertEquals(item, decoded)
        }
    }

    @Test
    fun tls13SignatureRestrictions() {
        // rsa-pkcs1 denied
        assertFalse(SignatureScheme.RSA_PKCS1_SHA1.supportedInTls13())
        assertFalse(SignatureScheme.RSA_PKCS1_SHA256.supportedInTls13())
        assertFalse(SignatureScheme.RSA_PKCS1_SHA384.supportedInTls13())
        assertFalse(SignatureScheme.RSA_PKCS1_SHA512.supportedInTls13())

        // dsa / anonymous denied
        assertFalse(SignatureScheme(0x0202u).supportedInTls13())
        assertFalse(SignatureScheme(0x0402u).supportedInTls13())
        assertFalse(SignatureScheme(0x0502u).supportedInTls13())
        assertFalse(SignatureScheme(0x0602u).supportedInTls13())

        // sha1, md5, none, sha224 denied
        assertFalse(SignatureScheme.ECDSA_SHA1_Legacy.supportedInTls13())
        assertFalse(SignatureScheme(0x0101u).supportedInTls13())
        assertFalse(SignatureScheme(0x0103u).supportedInTls13())
        assertFalse(SignatureScheme(0x0301u).supportedInTls13())
        assertFalse(SignatureScheme(0x0303u).supportedInTls13())

        // allowable
        assertTrue(SignatureScheme.RSA_PSS_SHA256.supportedInTls13())
        assertTrue(SignatureScheme.RSA_PSS_SHA384.supportedInTls13())
        assertTrue(SignatureScheme.RSA_PSS_SHA512.supportedInTls13())
        assertTrue(SignatureScheme.ED25519.supportedInTls13())
        assertTrue(SignatureScheme.ED448.supportedInTls13())
        assertTrue(SignatureScheme.ECDSA_NISTP256_SHA256.supportedInTls13())
        assertTrue(SignatureScheme.ECDSA_NISTP384_SHA384.supportedInTls13())
        assertTrue(SignatureScheme.ECDSA_NISTP521_SHA512.supportedInTls13())
    }

    @Test
    fun signatureSchemeAlgorithm() {
        assertEquals(SignatureAlgorithm.RSA, SignatureScheme.RSA_PKCS1_SHA1.algorithm())
        assertEquals(SignatureAlgorithm.RSA, SignatureScheme.RSA_PSS_SHA256.algorithm())
        assertEquals(SignatureAlgorithm.ECDSA, SignatureScheme.ECDSA_NISTP256_SHA256.algorithm())
        assertEquals(SignatureAlgorithm.ED25519, SignatureScheme.ED25519.algorithm())
        assertEquals(SignatureAlgorithm.ED448, SignatureScheme.ED448.algorithm())
        assertEquals(SignatureAlgorithm(0u), SignatureScheme(0x1234u).algorithm())
    }
}
