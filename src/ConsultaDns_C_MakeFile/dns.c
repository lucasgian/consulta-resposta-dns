/**
 * Programa para traduzir nome de serviço/servidor em endereços IP.
 * São traduzidos IPv4 e IPv6.
 *
 * Esse código é apenas um exemplo de uso de sockets
 *
 * Autor: Jonathan H.
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include "funcao.h"


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

    fprintf(stdout, "Endereço IP do %s:\n\n", argv[1]);

    /* Imprime de maneira legível os endereços encontrados */
    get_addresses(response);

    /* Free the linked list of addresses */
    freeaddrinfo(response);

    return EXIT_SUCCESS;
}




