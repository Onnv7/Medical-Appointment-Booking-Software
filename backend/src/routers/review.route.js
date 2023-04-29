import express from "express";
import { createReview, getReviewByDoctor, updateLiker } from "../controllers/review.controller.js"

const router = express.Router();

router.post('/create', createReview)
router.get('/list/:doctorId', getReviewByDoctor)
router.patch('/update-liker/:reviewId', updateLiker)
export default router;