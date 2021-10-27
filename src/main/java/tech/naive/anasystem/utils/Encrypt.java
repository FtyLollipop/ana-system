package tech.naive.anasystem.utils;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.util.encoders.Hex;

public class Encrypt {
    public static String encrypt(String password){
        return getSHA(getSHA(password)+"fh39sc0qj8c90dq");
    }

    public static String getSHA(String str) {
        byte[] bytes = str.getBytes();
        Digest digest = new SHA3Digest(256);

        digest.update(bytes, 0, bytes.length);

        byte[] rsData = new byte[digest.getDigestSize()];

        digest.doFinal(rsData, 0);

        return Hex.toHexString(rsData);

    }
}
