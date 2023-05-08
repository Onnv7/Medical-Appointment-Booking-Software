import mongoose from "mongoose";
import Review from "./review.model.js"
import Booking from "./booking.model.js"


const doctorSchema = mongoose.Schema({
    email: {
        type: String,
        unique: true,
        required: true,
    },
    password: {
        type: String,
        required: true,
        minLength: [
            6,
            "A user password must have more or equal than 6 characters",
        ],
    },
    name: {
        type: String,
        required: true,
    },
    gender: {
        type: String,
        required: true,
        enum: ["male", "female"],
    },
    birthDate: {
        type: Date,
        required: true,
    },
    avatarUrl: {
        type: String,
        required: true,
        default: "https://res.cloudinary.com/dtvnsczg8/image/upload/v1681134360/Clinic/avatar/doctor/doctor_avatar_default.png"
    },
    phone: {
        type: String,
        required: true,
    },
    introduction: {
        type: String,
        required: true,
    },
    clinicName: {
        type: String,
        required: true,
    },
    clinicAddress: {
        type: String,
        required: true,
    },
    price: {
        type: Number,
        required: true,
        default: 0
    },
    specialist: {
        type: mongoose.Types.ObjectId,
        ref: "Specialist",
        required: true,
        default: null
    }
}, { timestamps: true }, { toJSON: { virtuals: true } });

// doctorSchema.virtual('rating').get(async function () {
//     let rating = 0
//     try {
//         rating = await Booking.aggregate([
//             { $match: { doctor: this._id } },
//             { $group: { _id: null, avgRating: { $avg: '$star' } } }
//         ])
//             .exec()
//             .then(result => result.length ? result[0].avgRating : 0)
//     } catch (error) {
//         console.log(error)
//     }
//     finally {
//         return rating;
//     }
// });
doctorSchema.virtual('rating').get(async function () {
    const reviews = await Review.find({ doctor: this._id });
    const totalStars = reviews.reduce((sum, review) => sum + review.star, 0);
    return totalStars / reviews.length;
});
doctorSchema.virtual('quantity').get(async function () {
    const patient = await Booking.distinct('patient', { doctor: doctor._id })
    return patient.length;
});

export default mongoose.model("Doctor", doctorSchema);