package com.sqs.ecc;

import java.math.BigInteger;

public class EllipticCurveCryptography {

    private BigInteger a; // Parâmetro 'a' da equação da curva elíptica: y^2 = x^3 + ax + b
    @SuppressWarnings("unused")
    private BigInteger b; // Parâmetro 'b' da equação da curva elíptica: y^2 = x^3 + ax + b
    private BigInteger p; // O módulo primo que define o campo finito da curva elíptica

    public EllipticCurveCryptography(BigInteger a, BigInteger b, BigInteger p) {
	this.a = a;
	this.b = b;
	this.p = p;
    }

    // Adição de pontos na curva elíptica
    public BigInteger[] pointAddition(BigInteger[] P, BigInteger[] Q) {
	if (P[0].equals(Q[0]) && P[1].equals(Q[1])) {
	    // P é igual a Q, calculamos a tangente da curva no ponto P
	    BigInteger lambda = (P[0].pow(2).multiply(BigInteger.valueOf(3)).add(a))
		    .multiply(P[1].multiply(BigInteger.valueOf(2)).modInverse(p))
		    .mod(p);
	    // Calculamos as coordenadas do novo ponto
	    BigInteger xR = lambda.pow(2).subtract(P[0].multiply(BigInteger.valueOf(2))).mod(p);
	    BigInteger yR = lambda.multiply(P[0].subtract(xR)).subtract(P[1]).mod(p);
	    return new BigInteger[] { xR, yR };
	} else {
	    // P é diferente de Q, calculamos a inclinação da linha que passa por P e Q
	    BigInteger lambda = (Q[1].subtract(P[1])).multiply(Q[0].subtract(P[0]).modInverse(p)).mod(p);
	    // Calculamos as coordenadas do novo ponto
	    BigInteger xR = lambda.pow(2).subtract(P[0]).subtract(Q[0]).mod(p);
	    BigInteger yR = lambda.multiply(P[0].subtract(xR)).subtract(P[1]).mod(p);
	    return new BigInteger[] { xR, yR };
	}
    }

    // Multiplicação escalar de um ponto na curva elíptica
    public BigInteger[] doubleAndAdd(BigInteger[] P, BigInteger k) {
	BigInteger[] result = { BigInteger.ZERO, BigInteger.ZERO }; // Ponto na 'coordenada do infinito'

	// Aplicamos o método double-and-add
	for (int i = k.bitLength() - 1; i >= 0; i--) {
	    result = pointAddition(result, result); // Dobramos o ponto atual
	    if (k.testBit(i)) {
		result = pointAddition(result, P); // Adicionamos P ao resultado se o bit atual de k for 1
	    }
	}
	return result;
    }
}
