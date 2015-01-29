import requests

DNS_USER = "6a0c97f49ec7451f83ca"
DNS_SECRET = "QHWKhiNVs7DrLsDZ2skZOSGGSwIn3BUF3jZMOEDw"

DNS_URI = "https://api.dns4e.com/v7/{subdomain}.ailurus.ca/a"
TTL = 60 * 60


# Adds a type A record to the ailurus.ca domain
# Returns the record, or throws an exception on error
#
# Example usage:
# AddRecord('test0001', '99.99.99.1')
# Adds dns A entry: test0001.ailurus.ca -> 99.99.99.1
def AddRecord(subdomain, ip_addr):
    uri = DNS_URI.format(subdomain=subdomain)
    post_data = {
        'record': ip_addr,
        'ttl': TTL
    }
    result = requests.post(
        uri, data=post_data, auth=(DNS_USER, DNS_SECRET))
    result.raise_for_status()
    return result.json()


# Returns the records for an ailurus subdomain
# or throws an exception on error
def QueryRecord(subdomain):
    uri = DNS_URI.format(subdomain=subdomain)
    result = requests.get(uri, auth=(DNS_USER, DNS_SECRET))
    result.raise_for_status()
    return result.json()
