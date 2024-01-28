package com.sqs.ecc;

import java.util.Random;

public class App {

    public static void main(String[] args) {
	// ECC ecc = new ECC(0, 7);

	// Point generatior = new Point(1, 1);

	// System.out.println(ecc.pointAddition(generatior, generatior));

	// System.out.println(ecc.doubleAndAdd(100, generatior));

	ECC ecc = new ECC(3, 7);

	Point generatior = new Point(-2, 1);
	Random random = new Random();

	// Elliptic Curve Diffie-Hellman Algorithm
	// random number for Alice
	int a = random.nextInt(10000);
	// random number for Bob
	int b = random.nextInt(10000);

	Point alicePublicKey = ecc.doubleAndAdd(a, generatior);
	Point bobPublicKey = ecc.doubleAndAdd(b, generatior);

	Point aliceSecretKey = ecc.doubleAndAdd(a, bobPublicKey);
	Point bobSecretKey = ecc.doubleAndAdd(b, alicePublicKey);

	System.out.println(aliceSecretKey);
	System.out.println(bobSecretKey);

    }

}
