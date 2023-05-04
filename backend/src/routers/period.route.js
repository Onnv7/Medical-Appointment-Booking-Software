import express from "express";
import { getAllPeriods } from "../controllers/period.controller.js"
const router = express.Router();


router.get("/list", getAllPeriods)
export default router;