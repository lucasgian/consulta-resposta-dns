int main (int argc, char *argv[]);
void usage ();
void * get_in_addr (struct addrinfo *p);
void print_addrA (const char *ipstr, int family);
void print_addrAAAA (const char *ipstr, int family);
void get_addresses (struct addrinfo *response);