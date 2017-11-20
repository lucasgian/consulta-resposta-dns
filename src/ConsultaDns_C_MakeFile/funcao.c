#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>


/**
 * Imprime uma mensagem de como usar
 */
 void
 usage ()
 {
    fprintf(stderr, "Para usar: ./dns hostname\n");
}


/**
 * Retorna o endereço de memória da estrutura sin_addr (IPv4)
 * ou sin6_addr (IPv6)
 */
 void *
 get_in_addr (struct addrinfo *p)
 {
    /* IPv4 */
    if (p->ai_family == AF_INET) {
        struct sockaddr_in *ipv4 = (struct sockaddr_in *) p->ai_addr;
        return &(ipv4->sin_addr);
    }

    /* IPv6 */
    struct sockaddr_in6 *ipv6 = (struct sockaddr_in6 *) p->ai_addr;
    return &(ipv6->sin6_addr);
}


/**
 * Imprime o endereço IP de maneira legível
 */
 void
 print_addrA (const char *ipstr, int family)
 {

    if(family != AF_INET6) {
        fprintf(stdout, "\tA: %s\n", ipstr);
    }

    
}

/**
 * Imprime o endereço IP de maneira legível
 */
 void
 print_addrAAAA (const char *ipstr, int family)
 {

    if(family == AF_INET6) {
        fprintf(stdout, "\tAAAA: %s\n", ipstr);
    }

    
}


/**
 * Para cada endereço IP converte o endereço numérico (de octetos) para
 * uma string legível
 */
 void
 get_addresses (struct addrinfo *response)
 {
    /* Ponteiro de addinfo usado para iterar na lista */
    struct addrinfo *p;
    /* String do endereço */
    char ipstr[INET6_ADDRSTRLEN];

    for(p = response; p != NULL; p = p->ai_next) {

        /* Utiliza a função get_in_addr para colocar a string em ipstr */
        inet_ntop(p->ai_family, get_in_addr(p), ipstr, sizeof ipstr);
        
        /* Imprime o endereço corrente */
        print_addrA(ipstr, p->ai_family);
        print_addrAAAA(ipstr, p->ai_family);
    }
}