import express from "express";
import { bookingAppointment, updateProfile, updateAvatar } from "../controllers/patient.controller.js"

import multer from 'multer';
const router = express.Router();

const storage = multer.memoryStorage();
const upload = multer({ storage });

router.post('/booking/:bookingId', bookingAppointment);
router.put('/update-profile/:patientId', updateProfile)
router.patch('/update-avatar/:patientId', upload.single('avatar'), updateAvatar)
export default router;