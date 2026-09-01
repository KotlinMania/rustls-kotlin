// port-lint: tests error.rs
package io.github.kotlinmania.rustls

import io.github.kotlinmania.rustls.pki.ServerName
import io.github.kotlinmania.rustls.pki.UnixTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ErrorTest {
    @Test
    fun testErrorHierarchyAndFormatting() {
        val serverName = ServerName.tryFrom("example.com").getOrThrow()
        val errors: List<Error> =
            listOf(
                Error.InappropriateMessage(listOf(ContentType.Alert), ContentType.Handshake),
                Error.InappropriateHandshakeMessage(listOf(HandshakeType.ClientHello), HandshakeType.ServerHello),
                Error.InvalidMessage(InvalidMessage.InvalidCcs),
                Error.NoCertificatesPresented,
                Error.DecryptError,
                Error.PeerIncompatible(PeerIncompatible.Tls12NotOffered),
                Error.PeerMisbehaved(PeerMisbehaved.UnsolicitedCertExtension),
                Error.AlertReceived(AlertDescription.ExportRestriction),
                Error.InvalidCertificate(CertificateError.Expired),
                Error.InvalidCertificate(
                    CertificateError.NotValidForNameContext(
                        expected = serverName,
                        presented = emptyList(),
                    ),
                ),
                Error.InvalidCertificate(
                    CertificateError.NotValidForNameContext(
                        expected = serverName,
                        presented = listOf("hello.com"),
                    ),
                ),
                Error.InvalidCertificate(
                    CertificateError.NotValidYetContext(
                        time = UnixTime(300),
                        notBefore = UnixTime(320),
                    ),
                ),
                Error.InvalidCertificate(
                    CertificateError.ExpiredContext(
                        time = UnixTime(320),
                        notAfter = UnixTime(300),
                    ),
                ),
                Error.InvalidCertificate(
                    CertificateError.ExpiredRevocationListContext(
                        time = UnixTime(320),
                        nextUpdate = UnixTime(300),
                    ),
                ),
                Error.InvalidCertificate(CertificateError.InvalidOcspResponse),
                Error.General("undocumented error"),
                Error.FailedToGetCurrentTime,
                Error.FailedToGetRandomBytes,
                Error.HandshakeNotComplete,
                Error.PeerSentOversizedRecord,
                Error.NoApplicationProtocol,
                Error.BadMaxFragmentSize,
                Error.InconsistentKeys(InconsistentKeys.KeyMismatch),
                Error.InconsistentKeys(InconsistentKeys.Unknown),
                Error.InvalidCertRevocationList(CertRevocationListError.BadSignature),
                Error.Other(OtherError("custom error")),
            )

        for (err in errors) {
            assertTrue(err.message != null && err.message!!.isNotEmpty())
        }
    }

    @Test
    fun testAlertMapping() {
        assertEquals(AlertDescription.DecodeError, InvalidMessage.MessageTooShort.toAlertDescription())
        assertEquals(AlertDescription.IllegalParameter, InvalidMessage.PreSharedKeyIsNotFinalExtension.toAlertDescription())
        assertEquals(AlertDescription.UnsupportedExtension, InvalidMessage.UnknownHelloRetryRequestExtension.toAlertDescription())

        assertEquals(AlertDescription.BadCertificate, CertificateError.BadEncoding.toAlertDescription())
        assertEquals(AlertDescription.CertificateExpired, CertificateError.Expired.toAlertDescription())
        assertEquals(AlertDescription.CertificateRevoked, CertificateError.Revoked.toAlertDescription())
        assertEquals(AlertDescription.UnknownCA, CertificateError.UnknownIssuer.toAlertDescription())
        assertEquals(AlertDescription.BadCertificateStatusResponse, CertificateError.InvalidOcspResponse.toAlertDescription())
        assertEquals(AlertDescription.DecryptError, CertificateError.BadSignature.toAlertDescription())
        assertEquals(AlertDescription.UnsupportedCertificate, CertificateError.InvalidPurpose.toAlertDescription())
        assertEquals(AlertDescription.AccessDenied, CertificateError.ApplicationVerificationFailure.toAlertDescription())
    }
}
