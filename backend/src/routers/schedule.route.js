import express from "express";
import { createSchedule, updateSchedule, listSchedule, getDetailsOneSchedule, getScheduleByDate, createScheduleTools, getAvailableSchedule, getTimeSlotInDate } from "../controllers/schedule.controller.js"
const router = express.Router();

router.patch("/update/:doctorId", updateSchedule);
router.post("/create", createSchedule);
router.get("/list/:doctorId", listSchedule);
router.get("/details/:scheduleId", getDetailsOneSchedule);
router.get("/date/list/:doctorId", getScheduleByDate);
router.post("/available/date/:doctorId", getAvailableSchedule);
router.get("/available/slot/:doctorId", getTimeSlotInDate);
router.post("/doctor/create/schedule/:doctorId", createScheduleTools);

export default router;