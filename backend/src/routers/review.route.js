import express from "express";
import { createReview, getReviewByDoctor } from "../controllers/review.controller.js"

const router = express.Router();

router.post('/create', createReview)
router.get('/list/:doctorId', getReviewByDoctor)
export default router;