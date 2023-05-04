import express from 'express';
const router = express.Router();
import { updateBooking, createBooking, listBookingByPatient, getAllBooking, listBookingByDoctor, getHistoryList, createDataBookingTool, getHistoryAppointmentDetails, getBookingDetailsById } from "../controllers/booking.controller.js"

// chức năng cho patient
router.post("/create", createBooking);
router.patch("/update/:bookingId", updateBooking);
router.get("/list", getAllBooking);
router.get("/patient/list/:patientId", listBookingByPatient);
router.get("/doctor/schedule/:doctorId", listBookingByDoctor);
router.get("/history/list/:patientId", getHistoryList);
router.get("/history/details/:appointmentId", getHistoryAppointmentDetails);
router.get("/doctor/details/:bookingId", getBookingDetailsById);
router.post("/tool/create", createDataBookingTool);


export default router

