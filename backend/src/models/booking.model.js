import mongoose from "mongoose";

const bookingSchema = mongoose.Schema({
    patientId: {
        type: mongoose.Types.ObjectId,
        ref: "Patient"
    },
    doctorId: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor"
    },
    message: {
        type: String,
    },
    status: {
        type: String,
        enum: ["waiting", "accepted", "denied", "over"],
        default: "waiting"
    },
    advice: {
        type: String,
    },
    review: {
        type: String,
    },
    start: {
        type: Number,
    },
    scheduleId: {
        type: mongoose.Types.ObjectId,
        ref: "Schedule"
    }
}, { timestamps: true });
bookingSchema.index({ doctorId: 1, scheduleId: 1 }, { unique: true });

export default mongoose.model("Booking", bookingSchema);