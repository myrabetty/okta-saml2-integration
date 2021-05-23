#create keystore  to be used with okta

# add the okta certificate to the keystore
keytool -import -alias oktaCertificate -file okta.cert -keystore keystore.jks -storepass saml2sample

# add the relying party certificate to the keystore
openssl pkcs12 -export -in rp-certificate.crt -inkey rp-private.key -out server.p12 -name rp-signing -CAfile rp-ca.crt -caname root
keytool -importkeystore -deststorepass saml2sample -destkeypass saml2sample -destkeystore keystore.jks -srckeystore server.p12 -srcstoretype PKCS12 -srcstorepass export -alias rp-signing
