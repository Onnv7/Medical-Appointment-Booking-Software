import mongoose from "mongoose";
import Review from "./review.model.js"

const doctorSchema = mongoose.Schema({
    email: {
        type: String,
        unique: true
    },
    password: {
        type: String
    },
    name: {
        type: String
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
        ref: "Specialist"
    }
}, { timestamps: true });

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

export default mongoose.model("Doctor", doctorSchema);