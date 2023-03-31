import express from "express";
import { bookingAppointment, updateProfile } from "../controllers/patient.controller.js"

const router = express.Router();

router.post('/booking/:bookingId', bookingAppointment);
router.put('/update-profile/:patientId', updateProfile)
export default router;