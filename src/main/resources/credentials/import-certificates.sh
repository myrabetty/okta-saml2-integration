

keytool -import -alias oktaCertificate -file mycert.cer -keystore mycerts -storepass saml2sample


openssl pkcs12 -export -in rp-certificate.crt -inkey rp-private.key -out server.p12 -name rp-signing -CAfile rp-ca.crt -caname root

keytool -importkeystore -deststorepass saml2sample -destkeypass saml2sample -destkeystore keystore.jks -srckeystore server.p12 -srcstoretype PKCS12 -srcstorepass export -alias rp-signing
