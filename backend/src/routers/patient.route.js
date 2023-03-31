import express from "express";
import { bookingAppointment, updateProfile } from "../controllers/patient.controller.js"
import { uploadAvatar, uploadSpecialist } from "../utils/multer.js";
const router = express.Router();

router.post('/booking/:bookingId', bookingAppointment);
router.put('/update-profile/:patientId', uploadAvatar.single('avatar'), updateProfile)
export default router;