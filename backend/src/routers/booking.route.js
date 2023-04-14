import express from 'express';
const router = express.Router();
import { updateBooking, createBooking, listBookingByPatient, getAllBooking, listBookingByDoctor } from "../controllers/booking.controller.js"

// chức năng cho patient
router.post("/create", createBooking);
router.patch("/update/:bookingId", updateBooking);
router.get("/list", getAllBooking);
router.get("/patient/list/:patientId", listBookingByPatient);
router.get("/doctor/list/:doctorId", listBookingByDoctor);


export default router

