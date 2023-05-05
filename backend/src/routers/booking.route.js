import express from 'express';
const router = express.Router();
import { updateBooking, createBooking, listBookingByPatient, getAllBooking, listBookingByDoctor, getHistoryList, createDataBookingTool, getHistoryAppointmentDetails, getBookingDetailsById, listNewBooking } from "../controllers/booking.controller.js"

// chức năng cho patient
router.post("/create", createBooking);
router.patch("/update/:bookingId", updateBooking);

router.get("/list", getAllBooking);
router.get("/patient/list/:patientId", listBookingByPatient);
router.get("/history/list/:patientId", getHistoryList);
router.get("/history/details/:appointmentId", getHistoryAppointmentDetails);

router.post("/tool/create", createDataBookingTool);



router.get("/doctor/new-booking/:doctorId", listNewBooking);
router.get("/doctor/schedule/:doctorId", listBookingByDoctor);
router.get("/doctor/details/:bookingId", getBookingDetailsById);


export default router

