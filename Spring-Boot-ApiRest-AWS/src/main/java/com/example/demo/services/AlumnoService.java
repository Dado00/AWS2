package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.models.AlumnoModel;
import com.example.demo.repositories.AlumnoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository;

    String key = "ASIARQV3YPE22TI2TBCD";
    String secret = "DDKwJQw11SrMTiLVDZWZEcDU2sg+TYhfj6MIPhKm";
    String sessionToken = "FwoGZXIvYXdzEMz//////////wEaDAqV/4hJED4/o0ymwyLPAed5U5Sf63LfQRCOoIA2OABhk6+QjbG821myJ/BPHpol5In5m+MiApROf49nu8Ai/2IFtpBuxepeedL3E18xaJJ1wZCs0mAW3jRLt64PIqLBUjnUcCuYgvgjarYJaGnzWvtiFkrAbIhE8Km/NyuJBnE0jWobZ6kQ0qaejMJSSLFJlhiBtVCPeEP2FRioPLKVVFEDewIQKt3OyNfBzyoCFtxQi3IncZj0btJfCeTtT92RPAdtp7zaU1IL3WMiZt5jTg5bfJ5ftZoy2GfQsUibOCjwg+qcBjIt+LBfUMZI1+R/E/PbNiqY6tci5uvPyKJAtwdZGW3irSC12zBn2F+VeEW+WnBd";
    String bucket = "avilapacheco";

    // private String US_EAST_1;

    public AlumnoModel subirFotoPerfil(int id, MultipartFile foto) throws IOException {
        ArrayList<AlumnoModel> alumnoarray = new ArrayList();
        alumnoarray = alumnoRepository.findById(id);
        AlumnoModel alumno = alumnoarray.get(0);

        BasicSessionCredentials awsCreds = new BasicSessionCredentials(key, secret, sessionToken);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.US_EAST_1).build();
        String key = "fotos/" + alumno.getMatricula() + "_" + alumno.getNombres() + "_fotoPerfil_"
                + foto.getOriginalFilename();

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, foto.getInputStream(),
                new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(putObjectRequest);

        alumno.setFotoPerfilUrl("https://" + bucket + ".s3.amazonaws.com/" + key);

        return alumnoRepository.save(alumno);
    }

    public ArrayList<AlumnoModel> obtenerAlumnos() {
        return (ArrayList<AlumnoModel>) alumnoRepository.findAll();
    }

    public AlumnoModel guardarAlumno(AlumnoModel alumno) {
        return alumnoRepository.save(alumno);
    }

    public AlumnoModel modificarAlumno(AlumnoModel alumno) {
        return alumnoRepository.save(alumno);
    }

    public Optional<AlumnoModel> obtenerPorId(Integer id) {
        return alumnoRepository.findById(id);
    }

    public boolean eliminarAlumno(Integer id) {
        try {
            alumnoRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
