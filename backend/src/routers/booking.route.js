import express from 'express';
const router = express.Router();
import { updateBooking, createBooking, listBookingByPatient, getAllBooking, listBookingByDoctor, getHistoryList, createDataBookingTool, getHistoryAppointmentDetails } from "../controllers/booking.controller.js"

// chức năng cho patient
router.post("/create", createBooking);
router.patch("/update/:bookingId", updateBooking);
router.get("/list", getAllBooking);
router.get("/patient/list/:patientId", listBookingByPatient);
router.get("/doctor/list/:doctorId", listBookingByDoctor);
router.get("/history/list/:patientId", getHistoryList);
router.get("/history/details/:appointmentId", getHistoryAppointmentDetails);
router.post("/tool/create", createDataBookingTool);


export default router

