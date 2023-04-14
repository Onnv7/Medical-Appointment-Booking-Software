import express from 'express';
import multer from 'multer';

import { updateProfile, getProfileById, getTopDoctor, getSomeDoctor, searchDoctor, getInfoDoctorById, getReviewsByDoctorId, getSpecificationsDoctor } from "../controllers/doctor.controller.js"

const router = express.Router();


const storage = multer.memoryStorage();
const upload = multer({ storage });


// router.post("/create", createDoctor)
router.put("/update/:doctorId", upload.single('image'), updateProfile);
router.get("/profile/:doctorId", getProfileById);
router.get("/top-doctor/:top", getTopDoctor);
router.get("/some-doctor/:some", getSomeDoctor);
router.get("/search", searchDoctor);
router.get("/info/:doctorId", getInfoDoctorById)
router.get("/review/:doctorId", getReviewsByDoctorId)
router.get("/specifications/:doctorId", getSpecificationsDoctor)
// router.post("/schedule/create", createSchedule);


export default router;