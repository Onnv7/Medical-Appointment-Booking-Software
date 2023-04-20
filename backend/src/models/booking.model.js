import mongoose from "mongoose";

const bookingSchema = mongoose.Schema({
    patient: {
        type: mongoose.Types.ObjectId,
        ref: "Patient"
    },
    doctor: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor"
    },
    message: {
        type: String,
    },
    status: {
        type: String,
        enum: ["waiting", "accepted", "denied", "succeeded"],
        default: "waiting"
    },
    advice: {
        type: String,
    },
    review: {
        type: String,
    },
    star: {
        type: Number,
    },
    time: {
        type: String,
    }
}, { timestamps: true });
bookingSchema.index({ doctor: 1, time: 1 }, { unique: true });

export default mongoose.model("Booking", bookingSchema);