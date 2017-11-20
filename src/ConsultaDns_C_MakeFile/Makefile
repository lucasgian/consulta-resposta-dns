main:	dns.o	funcao.o
	gcc dns.o funcao.o -o dns

main.o:	dns.c
	gcc -c dns.c

funcao.o:	funcao.c	funcao.h
	gcc -c funcao.c

clean:
	rm *.o