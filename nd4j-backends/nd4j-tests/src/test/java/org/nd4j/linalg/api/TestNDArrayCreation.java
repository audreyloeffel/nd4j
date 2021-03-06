package org.nd4j.linalg.api;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Pointer;
import org.junit.Test;
import org.nd4j.linalg.BaseNd4jTest;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.factory.Nd4jBackend;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.nativeblas.NativeOps;
import org.nd4j.nativeblas.NativeOpsHolder;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alex on 30/04/2016.
 */

public class TestNDArrayCreation extends BaseNd4jTest {


    public TestNDArrayCreation(Nd4jBackend backend) {
        super(backend);
    }

    @Test
    public void testBufferCreation() {
        DataBuffer dataBuffer = Nd4j.createBuffer(new double[]{1,2});
        Pointer pointer = dataBuffer.pointer();
        FloatPointer floatPointer = new FloatPointer(pointer);
        DataBuffer dataBuffer1 = Nd4j.createBuffer(floatPointer,2);
        assertEquals(2,dataBuffer1.length());
        assertEquals(1.0,dataBuffer1.getDouble(0),1e-1);
        assertEquals(2.0,dataBuffer1.getDouble(1),1e-1);
        INDArray arr = Nd4j.create(dataBuffer1);
        System.out.println(arr);
    }


    @Test
    public void testCreateNpy() throws Exception {
        INDArray arrCreate = Nd4j.createFromNpyFile(new ClassPathResource("test.npy").getFile());
        assertEquals(2,arrCreate.size(0));
        assertEquals(2,arrCreate.size(1));
        assertEquals(1.0,arrCreate.getDouble(0,0),1e-1);
        assertEquals(2.0,arrCreate.getDouble(0,1),1e-1);
        assertEquals(3.0,arrCreate.getDouble(1,0),1e-1);
        assertEquals(4.0,arrCreate.getDouble(1,1),1e-1);

    }

    @Test
    public void testCreateNpy3() throws Exception {
        INDArray arrCreate = Nd4j.createFromNpyFile(new ClassPathResource("rank3.npy").getFile());
        assertEquals(8,arrCreate.length());
        assertEquals(3,arrCreate.rank());

        Pointer pointer = NativeOpsHolder.getInstance().getDeviceNativeOps().pointerForAddress(arrCreate.data().address());
        assertEquals(arrCreate.data().address(),pointer.address());
    }


    @Override
    public char ordering() {
        return 'c';
    }
}
