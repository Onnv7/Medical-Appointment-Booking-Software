import express from 'express';
const router = express.Router();
import { updateBooking, createBooking, listBookingForPatient, getAllBooking, listBookingForDoctor } from "../controllers/booking.cotroller.js"

router.post("/create", createBooking);
router.patch("/update/:bookingId", updateBooking);
router.get("/list", getAllBooking);
router.get("/patient/list/:patientId", listBookingForPatient);
router.get("/doctor/list/:doctorId", listBookingForDoctor);


export default router

