// port-lint: source error.rs
package io.github.kotlinmania.rustls

import io.github.kotlinmania.rustls.crypto.KeyExchangeAlgorithm
import io.github.kotlinmania.rustls.pki.AlgorithmIdentifier
import io.github.kotlinmania.rustls.pki.ServerName
import io.github.kotlinmania.rustls.pki.UnixTime

/**
 * Inconsistent keys error reason.
 */
public enum class InconsistentKeys {
    KeyMismatch,
    Unknown,
}

/**
 * Encapsulates an opaque or external error.
 */
public data class OtherError(
    public val message: String,
    public val cause: Throwable? = null,
) {
    override fun equals(other: Any?): Boolean = false

    override fun hashCode(): Int = message.hashCode()

    override fun toString(): String = message
}

/**
 * Extended Key Usage (EKU) purpose values.
 */
public sealed class ExtendedKeyPurpose {
    public object ClientAuth : ExtendedKeyPurpose() {
        override fun toString(): String = "client authentication"
    }

    public object ServerAuth : ExtendedKeyPurpose() {
        override fun toString(): String = "server authentication"
    }

    public data class Other(
        public val values: List<Int>,
    ) : ExtendedKeyPurpose() {
        override fun toString(): String = values.joinToString(", ")
    }
}

/**
 * Invalid message decoding errors.
 */
public sealed class InvalidMessage(
    message: String? = null,
) : Exception(message) {
    public object BorrowKeyData : InvalidMessage("borrow key data")

    public object ExpectedKeyUpdate : InvalidMessage("expected key update")

    public object HandshakePayloadTooLarge : InvalidMessage("handshake payload too large")

    public object InvalidCcs : InvalidMessage("invalid CCS")

    public object InvalidContentType : InvalidMessage("invalid content type")

    public object InvalidCertificateStatusType : InvalidMessage("invalid certificate status type")

    public object InvalidCertRequest : InvalidMessage("invalid certificate request")

    public object InvalidDhParams : InvalidMessage("invalid DH params")

    public object InvalidEmptyPayload : InvalidMessage("invalid empty payload")

    public object InvalidKeyUpdate : InvalidMessage("invalid key update")

    public object InvalidServerName : InvalidMessage("invalid server name")

    public object MessageTooLarge : InvalidMessage("message too large")

    public object MessageTooShort : InvalidMessage("message too short")

    public data class MissingData(
        public val name: String,
    ) : InvalidMessage("missing data: $name")

    public object MissingKeyExchange : InvalidMessage("missing key exchange")

    public object NoSignatureSchemes : InvalidMessage("no signature schemes")

    public data class TrailingData(
        public val name: String,
    ) : InvalidMessage("trailing data: $name")

    public data class UnexpectedMessage(
        public val name: String,
    ) : InvalidMessage("unexpected message: $name")

    public object UnknownProtocolVersion : InvalidMessage("unknown protocol version")

    public object UnsupportedCompression : InvalidMessage("unsupported compression")

    public object UnsupportedCurveType : InvalidMessage("unsupported curve type")

    public data class UnsupportedKeyExchangeAlgorithm(
        public val algorithm: KeyExchangeAlgorithm,
    ) : InvalidMessage("unsupported key exchange algorithm: $algorithm")

    public object EmptyTicketValue : InvalidMessage("empty ticket value")

    public data class IllegalEmptyList(
        public val context: String,
    ) : InvalidMessage("illegal empty list in $context")

    public object IllegalEmptyValue : InvalidMessage("illegal empty value")

    public data class DuplicateExtension(
        public val type: UShort,
    ) : InvalidMessage("duplicate extension: $type")

    public object PreSharedKeyIsNotFinalExtension : InvalidMessage("pre-shared key is not final extension")

    public object UnknownHelloRetryRequestExtension : InvalidMessage("unknown hello retry request extension")

    public object UnknownCertificateExtension : InvalidMessage("unknown certificate extension")

    public fun toAlertDescription(): AlertDescription =
        when (this) {
            is PreSharedKeyIsNotFinalExtension, is DuplicateExtension -> AlertDescription.IllegalParameter
            is UnknownHelloRetryRequestExtension -> AlertDescription.UnsupportedExtension
            else -> AlertDescription.DecodeError
        }
}

/**
 * Peer misbehavior error variants.
 */
public enum class PeerMisbehaved {
    AttemptedDowngradeToTls12WhenTls13IsSupported,
    BadCertChainExtensions,
    DisallowedEncryptedExtension,
    DuplicateClientHelloExtensions,
    DuplicateEncryptedExtensions,
    DuplicateHelloRetryRequestExtensions,
    DuplicateNewSessionTicketExtensions,
    DuplicateServerHelloExtensions,
    DuplicateServerNameTypes,
    EarlyDataAttemptedInSecondClientHello,
    EarlyDataExtensionWithoutResumption,
    EarlyDataOfferedWithVariedCipherSuite,
    HandshakeHashVariedAfterRetry,
    IllegalHelloRetryRequestWithEmptyCookie,
    IllegalHelloRetryRequestWithNoChanges,
    IllegalHelloRetryRequestWithOfferedGroup,
    IllegalHelloRetryRequestWithUnofferedCipherSuite,
    IllegalHelloRetryRequestWithUnofferedNamedGroup,
    IllegalHelloRetryRequestWithUnsupportedVersion,
    IllegalHelloRetryRequestWithWrongSessionId,
    IllegalHelloRetryRequestWithInvalidEch,
    IllegalMiddleboxChangeCipherSpec,
    IllegalTlsInnerPlaintext,
    IncorrectBinder,
    InvalidCertCompression,
    InvalidMaxEarlyDataSize,
    InvalidKeyShare,
    KeyEpochWithPendingFragment,
    KeyUpdateReceivedInQuicConnection,
    MessageInterleavedWithHandshakeMessage,
    MissingBinderInPskExtension,
    MissingKeyShare,
    MissingPskModesExtension,
    MissingQuicTransportParameters,
    OfferedDuplicateCertificateCompressions,
    OfferedDuplicateKeyShares,
    OfferedEarlyDataWithOldProtocolVersion,
    OfferedEmptyApplicationProtocol,
    OfferedIncorrectCompressions,
    PskExtensionMustBeLast,
    PskExtensionWithMismatchedIdsAndBinders,
    RefusedToFollowHelloRetryRequest,
    RejectedEarlyDataInterleavedWithHandshakeMessage,
    ResumptionAttemptedWithVariedEms,
    ResumptionOfferedWithVariedCipherSuite,
    ResumptionOfferedWithVariedEms,
    ResumptionOfferedWithIncompatibleCipherSuite,
    SelectedDifferentCipherSuiteAfterRetry,
    SelectedInvalidPsk,
    SelectedTls12UsingTls13VersionExtension,
    SelectedUnofferedApplicationProtocol,
    SelectedUnofferedCertCompression,
    SelectedUnofferedCipherSuite,
    SelectedUnofferedCompression,
    SelectedUnofferedKxGroup,
    SelectedUnofferedPsk,
    SelectedUnusableCipherSuiteForVersion,
    ServerEchoedCompatibilitySessionId,
    ServerHelloMustOfferUncompressedEcPoints,
    ServerNameDifferedOnRetry,
    ServerNameMustContainOneHostName,
    SignedKxWithWrongAlgorithm,
    SignedHandshakeWithUnadvertisedSigScheme,
    TooManyEmptyFragments,
    TooManyKeyUpdateRequests,
    TooManyRenegotiationRequests,
    TooManyWarningAlertsReceived,
    TooMuchEarlyDataReceived,
    UnexpectedCleartextExtension,
    UnsolicitedCertExtension,
    UnsolicitedEncryptedExtension,
    UnsolicitedSctList,
    UnsolicitedServerHelloExtension,
    WrongGroupForKeyShare,
    UnsolicitedEchExtension,
}

/**
 * Peer incompatibility error variants.
 */
public sealed class PeerIncompatible {
    public object EcPointsExtensionRequired : PeerIncompatible()

    public object ExtendedMasterSecretExtensionRequired : PeerIncompatible()

    public object IncorrectCertificateTypeExtension : PeerIncompatible()

    public object KeyShareExtensionRequired : PeerIncompatible()

    public object NamedGroupsExtensionRequired : PeerIncompatible()

    public object NoCertificateRequestSignatureSchemesInCommon : PeerIncompatible()

    public object NoCipherSuitesInCommon : PeerIncompatible()

    public object NoEcPointFormatsInCommon : PeerIncompatible()

    public object NoKxGroupsInCommon : PeerIncompatible()

    public object NoSignatureSchemesInCommon : PeerIncompatible()

    public object NullCompressionRequired : PeerIncompatible()

    public object ServerDoesNotSupportTls12Or13 : PeerIncompatible()

    public object ServerSentHelloRetryRequestWithUnknownExtension : PeerIncompatible()

    public object ServerTlsVersionIsDisabledByOurConfig : PeerIncompatible()

    public object SignatureAlgorithmsExtensionRequired : PeerIncompatible()

    public object SupportedVersionsExtensionRequired : PeerIncompatible()

    public object Tls12NotOffered : PeerIncompatible()

    public object Tls12NotOfferedOrEnabled : PeerIncompatible()

    public object Tls13RequiredForQuic : PeerIncompatible()

    public object UncompressedEcPointsRequired : PeerIncompatible()

    public object UnsolicitedCertificateTypeExtension : PeerIncompatible()

    public data class ServerRejectedEncryptedClientHello(
        public val retryConfigs: ByteArray? = null,
    ) : PeerIncompatible() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ServerRejectedEncryptedClientHello) return false
            return retryConfigs.contentEquals(other.retryConfigs)
        }

        override fun hashCode(): Int = retryConfigs?.contentHashCode() ?: 0
    }
}

/**
 * Certificate validation error variants.
 */
public sealed class CertificateError {
    public object BadEncoding : CertificateError()

    public object Expired : CertificateError()

    public data class ExpiredContext(
        public val time: UnixTime,
        public val notAfter: UnixTime,
    ) : CertificateError()

    public object NotValidYet : CertificateError()

    public data class NotValidYetContext(
        public val time: UnixTime,
        public val notBefore: UnixTime,
    ) : CertificateError()

    public object Revoked : CertificateError()

    public object UnhandledCriticalExtension : CertificateError()

    public object UnknownIssuer : CertificateError()

    public object UnknownRevocationStatus : CertificateError()

    public object ExpiredRevocationList : CertificateError()

    public data class ExpiredRevocationListContext(
        public val time: UnixTime,
        public val nextUpdate: UnixTime,
    ) : CertificateError()

    public object BadSignature : CertificateError()

    public object UnsupportedSignatureAlgorithm : CertificateError()

    public data class UnsupportedSignatureAlgorithmContext(
        public val signatureAlgorithmId: ByteArray,
        public val supportedAlgorithms: List<AlgorithmIdentifier>,
    ) : CertificateError() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is UnsupportedSignatureAlgorithmContext) return false
            return signatureAlgorithmId.contentEquals(other.signatureAlgorithmId) &&
                supportedAlgorithms == other.supportedAlgorithms
        }

        override fun hashCode(): Int = 31 * signatureAlgorithmId.contentHashCode() + supportedAlgorithms.hashCode()
    }

    public data class UnsupportedSignatureAlgorithmForPublicKeyContext(
        public val signatureAlgorithmId: ByteArray,
        public val publicKeyAlgorithmId: ByteArray,
    ) : CertificateError() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is UnsupportedSignatureAlgorithmForPublicKeyContext) return false
            return signatureAlgorithmId.contentEquals(other.signatureAlgorithmId) &&
                publicKeyAlgorithmId.contentEquals(other.publicKeyAlgorithmId)
        }

        override fun hashCode(): Int =
            31 * signatureAlgorithmId.contentHashCode() + publicKeyAlgorithmId.contentHashCode()
    }

    public object NotValidForName : CertificateError()

    public data class NotValidForNameContext(
        public val expected: ServerName,
        public val presented: List<String>,
    ) : CertificateError()

    public object InvalidPurpose : CertificateError()

    public data class InvalidPurposeContext(
        public val required: ExtendedKeyPurpose,
        public val presented: List<ExtendedKeyPurpose>,
    ) : CertificateError()

    public object InvalidOcspResponse : CertificateError()

    public object ApplicationVerificationFailure : CertificateError()

    public data class Other(
        public val error: OtherError,
    ) : CertificateError() {
        override fun equals(other: Any?): Boolean = false

        override fun hashCode(): Int = error.hashCode()
    }

    public fun toAlertDescription(): AlertDescription =
        when (this) {
            is BadEncoding, is UnhandledCriticalExtension, is NotValidForName, is NotValidForNameContext ->
                AlertDescription.BadCertificate
            is Expired, is ExpiredContext, is NotValidYet, is NotValidYetContext ->
                AlertDescription.CertificateExpired
            is Revoked -> AlertDescription.CertificateRevoked
            is UnknownIssuer, is UnknownRevocationStatus, is ExpiredRevocationList, is ExpiredRevocationListContext ->
                AlertDescription.UnknownCA
            is InvalidOcspResponse -> AlertDescription.BadCertificateStatusResponse
            is BadSignature, is UnsupportedSignatureAlgorithm,
            is UnsupportedSignatureAlgorithmContext, is UnsupportedSignatureAlgorithmForPublicKeyContext,
            ->
                AlertDescription.DecryptError
            is InvalidPurpose, is InvalidPurposeContext -> AlertDescription.UnsupportedCertificate
            is ApplicationVerificationFailure -> AlertDescription.AccessDenied
            is Other -> AlertDescription.CertificateUnknown
        }
}

/**
 * Certificate revocation list (CRL) error variants.
 */
public sealed class CertRevocationListError {
    public object BadSignature : CertRevocationListError()

    public object UnsupportedSignatureAlgorithm : CertRevocationListError()

    public data class UnsupportedSignatureAlgorithmContext(
        public val signatureAlgorithmId: ByteArray,
        public val supportedAlgorithms: List<AlgorithmIdentifier>,
    ) : CertRevocationListError() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is UnsupportedSignatureAlgorithmContext) return false
            return signatureAlgorithmId.contentEquals(other.signatureAlgorithmId) &&
                supportedAlgorithms == other.supportedAlgorithms
        }

        override fun hashCode(): Int = 31 * signatureAlgorithmId.contentHashCode() + supportedAlgorithms.hashCode()
    }

    public data class UnsupportedSignatureAlgorithmForPublicKeyContext(
        public val signatureAlgorithmId: ByteArray,
        public val publicKeyAlgorithmId: ByteArray,
    ) : CertRevocationListError() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is UnsupportedSignatureAlgorithmForPublicKeyContext) return false
            return signatureAlgorithmId.contentEquals(other.signatureAlgorithmId) &&
                publicKeyAlgorithmId.contentEquals(other.publicKeyAlgorithmId)
        }

        override fun hashCode(): Int =
            31 * signatureAlgorithmId.contentHashCode() + publicKeyAlgorithmId.contentHashCode()
    }

    public object InvalidCrlNumber : CertRevocationListError()

    public object InvalidRevokedCertSerialNumber : CertRevocationListError()

    public object IssuerInvalidForCrl : CertRevocationListError()

    public data class Other(
        public val error: OtherError,
    ) : CertRevocationListError() {
        override fun equals(other: Any?): Boolean = false

        override fun hashCode(): Int = error.hashCode()
    }

    public object ParseError : CertRevocationListError()

    public object UnsupportedCrlVersion : CertRevocationListError()

    public object UnsupportedCriticalExtension : CertRevocationListError()

    public object UnsupportedDeltaCrl : CertRevocationListError()

    public object UnsupportedIndirectCrl : CertRevocationListError()

    public object UnsupportedRevocationReason : CertRevocationListError()
}

/**
 * Encrypted Client Hello (ECH) errors.
 */
public enum class EncryptedClientHelloError {
    InvalidConfigList,
    NoCompatibleConfig,
    SniRequired,
}

/**
 * The main Rustls error class representing all TLS operation errors.
 */
public sealed class Error(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {
    public data class InappropriateMessage(
        public val expectTypes: List<ContentType>,
        public val gotType: ContentType,
    ) : Error("received unexpected message: got $gotType when expecting ${expectTypes.joinToString(" or ")}")

    public data class InappropriateHandshakeMessage(
        public val expectTypes: List<HandshakeType>,
        public val gotType: HandshakeType,
    ) : Error("received unexpected handshake message: got $gotType when expecting ${expectTypes.joinToString(" or ")}")

    public data class InvalidMessage(
        public val error: io.github.kotlinmania.rustls.InvalidMessage,
    ) : Error("received corrupt message: ${error.message}", error)

    public data class PeerIncompatible(
        public val error: io.github.kotlinmania.rustls.PeerIncompatible,
    ) : Error("peer is incompatible: $error")

    public data class PeerMisbehaved(
        public val error: io.github.kotlinmania.rustls.PeerMisbehaved,
    ) : Error("peer misbehaved: $error")

    public data class AlertReceived(
        public val alert: AlertDescription,
    ) : Error("received fatal alert: $alert")

    public data class InvalidCertificate(
        public val error: CertificateError,
    ) : Error("invalid peer certificate: $error")

    public data class InvalidCertRevocationList(
        public val error: CertRevocationListError,
    ) : Error("invalid certificate revocation list: $error")

    public object NoCertificatesPresented : Error("peer sent no certificates")

    public object UnsupportedNameType : Error("presented server name type was not supported")

    public object DecryptError : Error("cannot decrypt peer's message")

    public data class InvalidEncryptedClientHello(
        public val error: EncryptedClientHelloError,
    ) : Error("encrypted client hello failure: $error")

    public object EncryptError : Error("cannot encrypt message")

    public object PeerSentOversizedRecord : Error("peer sent excess record size")

    public object HandshakeNotComplete : Error("handshake not complete")

    public object NoApplicationProtocol : Error("peer does not support any known protocol")

    public object FailedToGetCurrentTime : Error("failed to get current time")

    public object FailedToGetRandomBytes : Error("failed to get random bytes")

    public object BadMaxFragmentSize : Error("the supplied max_fragment_size was too small or large")

    public data class InconsistentKeys(
        public val reason: io.github.kotlinmania.rustls.InconsistentKeys,
    ) : Error("keys may not be consistent: $reason")

    public data class General(
        public val error: String,
    ) : Error("unexpected error: $error")

    public data class Other(
        public val other: OtherError,
    ) : Error("other error: $other", other.cause) {
        override fun equals(other: Any?): Boolean = false

        override fun hashCode(): Int = this.other.hashCode()
    }
}
