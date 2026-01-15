def to_hex_array(s, key=0x7F):
    return '{ ' + ', '.join([f'0x{ord(c) ^ key:02X}' for c in s]) + ', 0x00 }'

print('// API_KEY')
print(f'unsigned char api_key[] = {to_hex_array("e8f1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6")};')
print('// HMAC_SECRET')
print(f'unsigned char hmac_secret[] = {to_hex_array("c1d2e3f4g5h6i7j8k9l0m1n2o3p4q5r6s7t8u9v0w1x2y3z4a5b6c7")};')
print('// BASE_URL')
print(f'unsigned char base_url[] = {to_hex_array("https://zerokeep.vercel.app/")};')
