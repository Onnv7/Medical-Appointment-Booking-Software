import mongoose from "mongoose";
import Review from "./review.model.js"
import Booking from "./booking.model.js"


const doctorSchema = mongoose.Schema({
    email: {
        type: String,
        unique: true,
        empty: false,
    },
    password: {
        type: String,
        empty: false,
    },
    name: {
        type: String,
        empty: false,
    },
    gender: {
        type: String,
        enum: ["male", "female"],
    },
    birthDate: {
        type: Date
    },
    avatarUrl: {
        type: String
    },
    phone: {
        type: String
    },
    introduction: {
        type: String
    },
    clinicName: {
        type: String
    },
    clinicAddress: {
        type: String
    },
    price: {
        type: Number
    },
    specialist: {
        type: mongoose.Types.ObjectId,
        ref: "Specialist",
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