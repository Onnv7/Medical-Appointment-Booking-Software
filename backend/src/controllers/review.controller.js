import Review from "../models/review.model.js";
import { formatMongooseTime } from "../utils/formatTime.js"
import Doctor from "../models/doctor.model.js";
import { sendEmail } from './../utils/sendEmail.js';
import Booking from "../models/booking.model.js";
import Schedule from "../models/schedule.model.js"


export const createReview = async (req, res, next) => {
    try {
        const { booking, ...others } = req.body;
        const review = new Review(others);
        const result = await review.save();
        const update = await Booking.findByIdAndUpdate(booking,
            { review: result._id }
        )
        res.status(200).json({ success: true, message: 'Review saved successfully', review: review });
    } catch (error) {
        next(error);
    }
}
export const getReviewByDoctor = async (req, res, next) => {
    try {
        const reviews = await Review.find({
            doctor: req.params.doctorId
        }).populate({
            path: 'patient',
            select: { name: 1, avatarUrl: 1 }
        })
        const data = reviews.map((review) => {
            const { doctor, updatedAt, ...others } = review._doc;
            const createdAt = formatMongooseTime('MMMM DD, YYYY', others.createdAt);
            return { ...others, createdAt }
        });
        res.status(200).json({ success: true, message: 'Review saved successfully', result: data });
    } catch (error) {
        next(error);
    }
}

export const updateLiker = async (req, res, next) => {
    try {
        const likerId = req.body.likerId;
        const review = await Review.findById(
            req.params.reviewId,
        ).populate({
            path: 'patient',
            select: { name: 1, avatarUrl: 1 }
        })
        if (review.liker.includes(likerId)) {
            const index = review.liker.indexOf(likerId);
            if (index === -1) {
                return res.status(400).send("Liker does not exist");
            }
            review.liker.splice(index, 1);
        }
        else {
            review.liker.push(likerId);
        }
        await review.save();
        res.status(200).json({ success: true, message: 'Review saved successfully', result: review });
    } catch (error) {
        next(error);
    }
}