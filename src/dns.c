/**
 * Programa para traduzir nome de serviço/servidor em endereços IP.
 * São traduzidos IPv4 e IPv6.
 *
 * Esse código é apenas um exemplo de uso de sockets
 *
 * Autor: Gustavo Pantuza
 */


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
    fprintf(stderr, "Usage: showip hostname\n");
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
print_addr (const char *ipstr, int family)
{
    char* ipversion = "IPv4";

    if(family == AF_INET6) {
        ipversion = "IPv6";
    }

    fprintf(stdout, "\t%s: %s\n", ipversion, ipstr);
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
        print_addr(ipstr, p->ai_family);
    }
}


/**
 * Função main. Ela verifica argumentos de linha de comando e faz a chamada
 * de getaddrinfo para fazer a busca DNS na Internet.
 */
int
main (int argc, char *argv[])
{

    /* Estrutura para a requisição */
    struct addrinfo request;
    /* Estrutura para a resposta */
    struct addrinfo *response;
    /* Status da execução da função getaddrinfo() */
    int status;

    if (argc != 2) {
        usage();
        return EXIT_FAILURE;
    }

    memset(&request, 0, sizeof request); // Zerando a memória

    /* Família do protocolo: IPv4 -> AF_INET, IPv6 -> AF_INET6 */
    request.ai_family = AF_UNSPEC; // Nesse caso, qualquer um dos dois
    /* Quando for TCP -> SOCK_STREAM. Para UDP -> SOCK_DGRAM */
    request.ai_socktype = SOCK_STREAM;

    /* Busca as informações do domínio através dos servidores DNS */
    status = getaddrinfo(argv[1], NULL, &request, &response);

    if (status != 0) {
        fprintf(stderr, "getaddrinfo failure: %s\n", gai_strerror(status));
        return EXIT_FAILURE;
    }

    fprintf(stdout, "IP addresses from %s:\n\n", argv[1]);

    /* Imprime de maneira legível os endereços encontrados */
    get_addresses(response);

    /* Free the linked list of addresses */
    freeaddrinfo(response);

    return EXIT_SUCCESS;
}