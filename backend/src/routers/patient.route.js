import express from "express";
import { updateProfile, updateAvatar, getProfileById, isExistedPatient } from "../controllers/patient.controller.js"

import multer from 'multer';
const router = express.Router();

const storage = multer.memoryStorage();
const upload = multer({ storage });

// Viết đặt cuộc hẹn bên booking
// router.patch('/booking/:bookingId', bookingAppointment);
router.put('/update-profile/:patientId', upload.single('avatar'), updateProfile)
router.patch('/update-avatar/:patientId', upload.single('avatar'), updateAvatar)
router.get('/profile/:patientId', getProfileById)
router.get("/existed", isExistedPatient);

export default router;