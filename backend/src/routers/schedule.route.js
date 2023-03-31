import express from "express";
import { createSchedule, updateSchedule, listSchedule, getDetailsOneSchedule } from "../controllers/schedule.controller.js"
const router = express.Router();

router.post("/create", createSchedule);
router.get("/list/:doctorId", listSchedule);
router.get("/details/:scheduleId", getDetailsOneSchedule);
router.patch("/update/:scheduleId", updateSchedule);

export default router;